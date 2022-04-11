package by.godevelopment.currencyappsample.domain.usecase

import by.godevelopment.currencyappsample.commons.EMPTY_STRING
import by.godevelopment.currencyappsample.domain.helpers.DataHelpers
import by.godevelopment.currencyappsample.domain.models.CurrenciesDataModel
import by.godevelopment.currencyappsample.domain.models.ItemCurrencyModel
import by.godevelopment.currencyappsample.domain.repositories.CurrencyRep
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class GetAllRatesUseCase @Inject constructor(
    private val currencyRep: CurrencyRep,
    private val dataHelpers: DataHelpers
) : BaseUseCase<CurrenciesDataModel, EmptyParams>() {
    override suspend fun run(params: EmptyParams): CurrenciesDataModel =
        coroutineScope {
            val today = dataHelpers.getCurrentDayString()
            val yesterday = dataHelpers.getYesterdayDateString()
            val deferredNewRates = async {
                currencyRep.fetchAllRatesByDate(today)
            }
            val deferredOldRates = async {
                currencyRep.fetchAllRatesByDate(yesterday)
            }
            val currenciesNew = deferredNewRates.await()
            val currenciesOld = deferredOldRates.await()
            val currencyItems: MutableList<ItemCurrencyModel> = mutableListOf()
            var oldData = EMPTY_STRING
            var newData = EMPTY_STRING
            if (currenciesNew.isNotEmpty() && currenciesOld.isNotEmpty()) {
                for (newItemRate in currenciesNew) {
                    val oldItemRate = currenciesOld.firstOrNull { old ->
                        old.curId == newItemRate.curId
                    }
                    oldItemRate?.let {
                        currencyItems.add(
                            ItemCurrencyModel(
                                curId = newItemRate.curId,
                                abbreviation = newItemRate.abbreviation,
                                scale = newItemRate.scale,
                                curName = newItemRate.name,
                                curValueOld = oldItemRate.officialRate.toString(),
                                curValueNew = newItemRate.officialRate.toString()
                            )
                        )
                    }
                }
                oldData = dataHelpers.convertDateStringToString(currenciesOld.first().date)
                newData = dataHelpers.convertDateStringToString(currenciesNew.first().date)
            }
            CurrenciesDataModel(
                EMPTY_STRING,
                oldData,
                newData,
                currencyItems
            )
        }
}