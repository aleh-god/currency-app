package by.godevelopment.currencyappsample.data.repositories

import android.util.Log
import by.godevelopment.currencyappsample.commons.*
import by.godevelopment.currencyappsample.data.datamodels.*
import by.godevelopment.currencyappsample.data.datasources.DataInitSettingsSource
import by.godevelopment.currencyappsample.data.datasources.database.CurrencyDao
import by.godevelopment.currencyappsample.data.datasources.database.RateCurrencyDao
import by.godevelopment.currencyappsample.data.datasources.database.SettingsDao
import by.godevelopment.currencyappsample.data.datasources.network.RemoteDataSource
import by.godevelopment.currencyappsample.domain.repositories.CurrencyRep
import by.godevelopment.currencyappsample.domain.repositories.SettingsRep
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
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
    private var currencyCache: List<CurrencyApiModel> = emptyList()
    private var rateCache = mutableMapOf<String, List<RateCurrencyApiModel>>()

    override suspend fun fetchAllCurrencies(): List<CurrencyApiModel> {
        var result: List<CurrencyApiModel> = emptyList()
        try {
            Log.i(TAG, "CurrencyRepositoryImp fetchAllCurrencies: try")
            if (currencyCache.isEmpty()) {
                val insertData = remoteDataSource.fetchAllCurrencies().also {
                    Log.i(TAG, "CurrencyRepositoryImp fetchAllCurrencies: fetch = ${it.size}")
                    cacheMutex.withLock {
                        currencyCache = it
                    }
                    result = it
                }
                val logDelete = currencyDao.deleteAll()
                Log.i(TAG, "CurrencyRepositoryImp fetchAllCurrencies: logDelete = $logDelete")
                val logInsert = currencyDao.insertAllCurrencies(
                    insertData.map { model ->
                        convertCurrencyFromApiModelToDaoEntity(model)
                    }
                )
                Log.i(TAG, "CurrencyRepositoryImp fetchAllCurrencies: .insert = ${logInsert.size}")
            } else {
                Log.i(TAG, "CurrencyRepositoryImp fetchAllCurrencies: else, cache size = ${currencyCache.size}")
                result = currencyCache
            }
        } catch(e: Exception) {
            val query = currencyDao.getAllCurrencies()
            Log.i(TAG, "CurrencyRepositoryImp fetchAllCurrencies: catch, query size = ${query.size}")
            if (query.isNotEmpty()) result = query.map {
                convertCurrencyFromDaoEntityToApiModel(it)
            }
        }
        return result
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

    private fun convertCurrencyFromApiModelToDaoEntity(model: CurrencyApiModel): CurrencyEntity {
        return CurrencyEntity(
            id = 0,
            abbreviation = model.abbreviation,
            code = model.code,
            curId = model.curId,
            name = model.name,
            nameBel = model.nameBel,
            nameBelMulti = model.nameBelMulti,
            nameEng = model.nameEng,
            nameEngMulti = model.nameEngMulti,
            nameMulti = model.nameMulti,
            parentID = model.parentID,
            periodicity = model.periodicity,
            quotName = model.quotName,
            quotNameBel = model.quotNameBel,
            quotNameEng = model.quotNameEng,
            scale = model.scale,
            dateRecord = model.dateEnd
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

    private fun convertCurrencyFromDaoEntityToApiModel(entity: CurrencyEntity): CurrencyApiModel {
        return CurrencyApiModel(
            abbreviation = entity.abbreviation,
            code = entity.code,
            curId = entity.curId,
            name = entity.name,
            nameBel = entity.nameBel,
            nameBelMulti = entity.nameBelMulti,
            nameEng = entity.nameEng,
            nameEngMulti = entity.nameEngMulti,
            nameMulti = entity.nameMulti,
            parentID = entity.parentID,
            periodicity = entity.periodicity,
            quotName = entity.quotName,
            quotNameBel = entity.quotNameBel,
            quotNameEng = entity.quotNameEng,
            scale = entity.scale,
            dateEnd = entity.dateRecord,
            dateStart = entity.dateRecord
        )
    }

    override suspend fun loadSettings(reset: Boolean): Flow<List<ItemSettingsEntity>> =
        settingsDao.getAllSettings()
            .map { listDao ->
                Log.i(TAG, "CurrencyRepositoryImp loadSettings: reset = $reset")
                if (!reset) {
                    Log.i(TAG, "CurrencyRepositoryImp loadSettings: list = ${listDao.size}")
                    listDao.ifEmpty {
                        val listApi = fetchAllCurrencies()
                        createInitSettings(listApi)
                    }
                } else {
                    val listApi = fetchAllCurrencies()
                    createInitSettings(listApi)
                }
            }

    override suspend fun saveSettings(settings: List<ItemSettingsEntity>) {
        Log.i(TAG, "CurrencyRepositoryImp saveSettings: size = ${settings.size}")
        settingsDao.deleteAll()
        val logInsert = settingsDao.insertAllSettings(settings)
        Log.i(TAG, "CurrencyRepositoryImp saveSettings: insert = ${logInsert.size}")
    }

    private fun createInitSettings(list: List<CurrencyApiModel>): List<ItemSettingsEntity> {
        var countOrder = 3
        Log.i(TAG, "CurrencyRepositoryImp createInitSettings: list size = ${list.size}")
        val result = list
            .filter {
                it.dateEnd == CUR_LAST_DAY
            }
            .filter {
                it.curId != ID_EUR && it.curId != ID_USD && it.curId != ID_RUR
            }
            .map {
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
        Log.i(TAG, "CurrencyRepositoryImp createInitSettings: result size = ${result.size}")
        return DataInitSettingsSource.listCurrenciesVisibleByDefault + result
    }
}
