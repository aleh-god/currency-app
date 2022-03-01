package by.godevelopment.currencyappsample.data.datasources.network

import by.godevelopment.currencyappsample.data.datamodels.CurrencyApiModel
import by.godevelopment.currencyappsample.data.datamodels.RateCurrencyApiModel
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val currencyApi: CurrencyApi
) {
    suspend fun fetchAllCurrencies(): List<CurrencyApiModel> =
        currencyApi.getAllCurrencies()

    suspend fun fetchAllRatesByDate(dateString: String): List<RateCurrencyApiModel> =
        coroutineScope {
            val deferredRatesByDate = async {
                currencyApi.getAllRatesByDate(dateString)
            }
            val deferredRatesByMonth = async {
                currencyApi.getAllRatesByMonth(dateString)
            }

            val ratesByDate = deferredRatesByDate.await()
            val ratesByMonth = deferredRatesByMonth.await()
            ratesByDate + ratesByMonth
        }
}