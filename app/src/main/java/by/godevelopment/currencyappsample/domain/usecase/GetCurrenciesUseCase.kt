package by.godevelopment.currencyappsample.domain.usecase

import android.util.Log
import by.godevelopment.currencyappsample.R
import by.godevelopment.currencyappsample.commons.TAG
import by.godevelopment.currencyappsample.domain.helpers.DataHelpers
import by.godevelopment.currencyappsample.domain.helpers.StringHelper
import by.godevelopment.currencyappsample.domain.models.CurrenciesDataModel
import by.godevelopment.currencyappsample.domain.models.ItemCurrencyModel
import by.godevelopment.currencyappsample.domain.repositories.CurrencyRep
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class GetCurrenciesUseCase @Inject constructor(
    private val currencyRep: CurrencyRep,
    private val dataHelpers: DataHelpers,
    private val stringHelper: StringHelper
) : BaseUseCase<CurrenciesDataModel, EmptyParams>() {
    override suspend fun run(params: EmptyParams): CurrenciesDataModel =
        coroutineScope {
            val today = dataHelpers.getCurrentDayString()
            val yesterday = dataHelpers.getYesterdayDateString()
            Log.i(TAG, "GetCurrenciesUseCase run: today = $today & yesterday = $yesterday")

            val deferredNewRates = async {
                currencyRep.fetchAllRatesByDate(today)
            }
            val deferredOldRates = async {
                currencyRep.fetchAllRatesByDate(yesterday)
            }
            val currenciesNew = deferredNewRates.await()
            val currenciesOld = deferredOldRates.await()
            Log.i(TAG, "GetCurrenciesUseCase run: currenciesNew.size = ${currenciesNew.size}")
            Log.i(TAG, "GetCurrenciesUseCase run: currenciesOld.size = ${currenciesOld.size}")

            val currencyItems: MutableList<ItemCurrencyModel> = mutableListOf()
            for (newItemRate in currenciesNew) {
                val oldItemRate = currenciesOld.firstOrNull { old ->
                    old.id == newItemRate.id
                }
//                Log.i(TAG, "GetCurrenciesUseCase run: newItemRate in currenciesNew $oldItemRate")
                oldItemRate?.let {
                    currencyItems.add(
                        ItemCurrencyModel(
                            abbreviation = newItemRate.abbreviation,
                            curName = newItemRate.name,
                            curValueOld = oldItemRate.officialRate.toString(),
                            curValueNew = newItemRate.officialRate.toString()
                        )
                    )
                }
            }
            CurrenciesDataModel(
                header = stringHelper.getString(R.string.header_rate),
                oldData = yesterday,
                newData = today,
                currencyItems
            )
        }
}