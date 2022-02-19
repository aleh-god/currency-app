package by.godevelopment.currencyappsample.data.datasources.network

import by.godevelopment.currencyappsample.data.datamodels.CurrencyApiModel
import by.godevelopment.currencyappsample.data.datamodels.RateCurrencyApiModel
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val currencyApi: CurrencyApi
) {
    suspend fun fetchAllCurrencies(): List<CurrencyApiModel> =
        currencyApi.getAllCurrencies()

    suspend fun fetchAllRatesByDate(dateString: String): List<RateCurrencyApiModel> =
        currencyApi.getAllRatesByDate(dateString)
}