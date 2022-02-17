package by.godevelopment.currencyappsample.data.repositories

import android.util.Log
import by.godevelopment.currencyappsample.commons.TAG
import by.godevelopment.currencyappsample.data.datamodels.CurrencyApiModel
import by.godevelopment.currencyappsample.data.datamodels.RateCurrencyApiModel
import by.godevelopment.currencyappsample.data.datasources.network.RemoteDataSource
import by.godevelopment.currencyappsample.domain.repositories.CurrencyRep
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class CurrencyRepositoryImp @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val coroutineScope: CoroutineScope
) : CurrencyRep {
    override suspend fun fetchAllCurrencies(): List<CurrencyApiModel> =
        remoteDataSource.fetchAllCurrencies()

    override suspend fun fetchAllRatesNew(): List<RateCurrencyApiModel> =
        remoteDataSource.fetchAllRatesNew()

    override suspend fun fetchAllRatesOld(oldDate: String): List<RateCurrencyApiModel> =
        remoteDataSource.fetchAllRatesOld(oldDate)

    init {
        coroutineScope.launch {
            val test = fetchAllRatesNew()
            Log.i(TAG, "CurrencyRepositoryImp: fetchAllRatesNew() size = ${test.size}")
            Log.i(TAG, "CurrencyRepositoryImp: fetchAllRatesNew() $test")
        }

    }
}