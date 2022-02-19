package by.godevelopment.currencyappsample.domain.repositories

import by.godevelopment.currencyappsample.data.datamodels.CurrencyApiModel
import by.godevelopment.currencyappsample.data.datamodels.RateCurrencyApiModel

interface CurrencyRep {
    suspend fun fetchAllCurrencies(): List<CurrencyApiModel>
    suspend fun fetchAllRatesByDate(date: String): List<RateCurrencyApiModel>
}
