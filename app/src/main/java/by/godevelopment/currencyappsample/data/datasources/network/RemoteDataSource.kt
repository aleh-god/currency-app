package by.godevelopment.currencyappsample.data.datasources.network

import by.godevelopment.currencyappsample.data.datamodels.CurrencyApiModel
import by.godevelopment.currencyappsample.data.datamodels.RateCurrencyApiModel
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val currencyApi: CurrencyApi
) {
    suspend fun fetchAllCurrencies(): List<CurrencyApiModel> =
        currencyApi.getAllCurrencies()

    suspend fun fetchAllRatesNew(): List<RateCurrencyApiModel> =
        currencyApi.getAllRatesNew()

    suspend fun fetchAllRatesOld(dateString: String): List<RateCurrencyApiModel> =
        currencyApi.getAllRatesOld(dateString)
}