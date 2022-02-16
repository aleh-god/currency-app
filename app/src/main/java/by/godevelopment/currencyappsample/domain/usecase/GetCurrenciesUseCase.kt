package by.godevelopment.currencyappsample.domain.usecase

import by.godevelopment.currencyappsample.R
import by.godevelopment.currencyappsample.domain.helpers.DataHelpers
import by.godevelopment.currencyappsample.domain.helpers.StringHelper
import by.godevelopment.currencyappsample.domain.models.CurrenciesDataModel
import by.godevelopment.currencyappsample.domain.models.ItemCurrencyModel
import by.godevelopment.currencyappsample.domain.repositories.CurrencyRep
import javax.inject.Inject

class GetCurrenciesUseCase @Inject constructor(
    private val currencyRep: CurrencyRep,
    private val dataHelpers: DataHelpers,
    private val stringHelper: StringHelper
) : BaseUseCase<CurrenciesDataModel, EmptyParams>() {
    override suspend fun run(params: EmptyParams): CurrenciesDataModel {
        val yesterday = dataHelpers.getYesterdayDateString()
        val currenciesNew = currencyRep.fetchAllRatesNew()
        val currenciesOld = currencyRep.fetchAllRatesOld(yesterday)
        val newsItems: MutableList<ItemCurrencyModel> = mutableListOf()

        for (newItemRate in currenciesNew) {
            val oldItemRate = currenciesOld.first { old ->
                old.abbreviation == newItemRate.abbreviation
            }
            newsItems.add(
                ItemCurrencyModel(
                    abbreviation = newItemRate.abbreviation,
                    curName = newItemRate.name,
                    curValueOld = oldItemRate.officialRate.toString(),
                    curValueNew = newItemRate.officialRate.toString()
                )
            )

        }
        return CurrenciesDataModel(
            header = stringHelper.getString(R.string.header_rate),
            oldData = yesterday,
            newData = dataHelpers.getCurrentDayString(),
            newsItems
        )
    }
}