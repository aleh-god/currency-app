package by.godevelopment.currencyappsample.data.repositories

import android.util.Log
import by.godevelopment.currencyappsample.commons.*
import by.godevelopment.currencyappsample.data.datamodels.CurrencyApiModel
import by.godevelopment.currencyappsample.data.datamodels.ItemSettingsEntity
import by.godevelopment.currencyappsample.data.datamodels.RateCurrencyApiModel
import by.godevelopment.currencyappsample.data.datamodels.RateCurrencyEntity
import by.godevelopment.currencyappsample.data.datasources.DataInitSettingsSource
import by.godevelopment.currencyappsample.data.datasources.database.CurrencyDao
import by.godevelopment.currencyappsample.data.datasources.database.RateCurrencyDao
import by.godevelopment.currencyappsample.data.datasources.database.SettingsDao
import by.godevelopment.currencyappsample.data.datasources.network.RemoteDataSource
import by.godevelopment.currencyappsample.domain.repositories.CurrencyRep
import by.godevelopment.currencyappsample.domain.repositories.SettingsRep
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject

class CurrencyRepositoryImp @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val rateCurrencyDao: RateCurrencyDao,
    private val currencyDao: CurrencyDao,
    private val settingsDao: SettingsDao,
    private val coroutineScope: CoroutineScope
) : CurrencyRep, SettingsRep {

    private val cacheMutex = Mutex()
    private var rateCache = mutableMapOf<String, List<RateCurrencyApiModel>>()

    init {
        coroutineScope.launch {
            val check = settingsDao.getAllSettings().firstOrNull()
            if (check.isNullOrEmpty()) createSettings()
        }
    }

    override suspend fun fetchAllCurrencies(): List<CurrencyApiModel> =
        try {
            Log.i(TAG, "CurrencyRepositoryImp fetchAllCurrencies: start")
            remoteDataSource.fetchAllCurrencies()
        } catch(e: Exception) {
            Log.i(TAG, "CurrencyRepositoryImp fetchAllCurrencies: catch")
            emptyList()
        }

    override suspend fun fetchAllRatesByDate(date: String): List<RateCurrencyApiModel> {
        var result: List<RateCurrencyApiModel> = emptyList()
        try {
            val dateCache = rateCache[date]
            Log.i(TAG, "CurrencyRepositoryImp fetchAllRatesByDate: try")
            if (dateCache != null) {
                Log.i(TAG, "CurrencyRepositoryImp fetchAllRatesByDate: else, cache size = ${rateCache.size}")
                result = dateCache
            } else {
                val insertData = remoteDataSource.fetchAllRatesByDate(date).also {
                    Log.i(TAG, "CurrencyRepositoryImp fetchAllRatesByDate: fetch = ${it.size}")
                    cacheMutex.withLock {
                        rateCache[date] = it
                    }
                    result = it
                }
                val logDelete = rateCurrencyDao.deleteAllByDate(date + EMPTY_TIME)
                Log.i(TAG, "CurrencyRepositoryImp fetchAllRatesByDate: delete = $logDelete")
                val logInsert = rateCurrencyDao.insertAllRates(
                    insertData.map { model ->
                        convertRateFromApiModelToDaoEntity(model)
                    }
                )
                Log.i(TAG, "CurrencyRepositoryImp fetchAllRatesByDate: .insert = ${logInsert.size}")
            }
        } catch(e: Exception) {
            val query = rateCurrencyDao.getAllRatesByDate(date + EMPTY_TIME)
            Log.i(TAG, "CurrencyRepositoryImp fetchAllRatesByDate: catch, query size = ${query.size}")
            if (query.isNotEmpty()) result = query.map {
                convertRateFromDaoEntityToApiModel(it)
            }
        }
        return result
    }

    private fun convertRateFromApiModelToDaoEntity(model: RateCurrencyApiModel): RateCurrencyEntity {
        return RateCurrencyEntity(
            id = 0,
            abbreviation = model.abbreviation,
            curId = model.curId,
            name = model.name,
            officialRate = model.officialRate,
            scale = model.scale,
            dateRecord = model.date
        )
    }

    private fun convertRateFromDaoEntityToApiModel(entity: RateCurrencyEntity): RateCurrencyApiModel {
        return RateCurrencyApiModel(
            abbreviation = entity.abbreviation,
            curId = entity.curId,
            name = entity.name,
            officialRate = entity.officialRate,
            scale = entity.scale,
            date = entity.dateRecord
        )
    }

    override suspend fun loadSettings(reset: Boolean): Flow<List<ItemSettingsEntity>>
        = if (reset) {
            createSettings()
            settingsDao.getAllSettings()
        } else settingsDao.getAllSettings()

    override suspend fun saveSettings(settings: List<ItemSettingsEntity>) {
        Log.i(TAG, "CurrencyRepositoryImp saveSettings: size = ${settings.size}")
        settingsDao.deleteAll()
        val logInsert = settingsDao.insertAllSettings(settings)
        Log.i(TAG, "CurrencyRepositoryImp saveSettings: insert = ${logInsert.size}")
    }

    private suspend fun createSettings() {
        val listApi = fetchAllCurrencies()
        if (listApi.isNotEmpty()) {
            Log.i(TAG, "CurrencyRepositoryImp createSettings: list size = ${listApi.size}")
            val (other, default) = listApi
                .filter {
                    it.dateEnd == CUR_LAST_DAY
                }
                .partition {
                    it.curId != ID_EUR && it.curId != ID_USD && it.curId != ID_RUR
                }
            Log.i(TAG, "CurrencyRepositoryImp createInitSettings:" +
                    " default = ${default.size} other = ${other.size}"
            )
            var countOrder = 0
            val defaultSettings = default
                .map {
                    ItemSettingsEntity(
                        id = 0,
                        curId = it.curId,
                        abbreviation = it.abbreviation,
                        name = it.name,
                        scale = it.scale,
                        isVisible = true,
                        orderPosition = countOrder++
                    )
                }
            val otherSettings = other.map {
                ItemSettingsEntity(
                    id = 0,
                    curId = it.curId,
                    abbreviation = it.abbreviation,
                    name = it.name,
                    scale = it.scale,
                    isVisible = false,
                    orderPosition = countOrder++
                )
            }
            saveSettings(otherSettings + defaultSettings)
        } else {
            saveSettings(DataInitSettingsSource.listCurrenciesVisibleByDefault)
        }
    }
}
