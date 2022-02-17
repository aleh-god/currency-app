package by.godevelopment.currencyappsample.domain.usecase

import android.util.Log
import by.godevelopment.currencyappsample.R
import by.godevelopment.currencyappsample.commons.TAG
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
        Log.i(TAG, "GetCurrenciesUseCase run: currenciesNew.size = ${currenciesNew.size}")
        val currenciesOld = currencyRep.fetchAllRatesOld(yesterday)
        Log.i(TAG, "GetCurrenciesUseCase run: currenciesOld.size = ${currenciesOld.size}")
        val currencyItems: MutableList<ItemCurrencyModel> = mutableListOf()

        for (newItemRate in currenciesNew) {
            val oldItemRate = currenciesOld.firstOrNull { old ->
                old.id == newItemRate.id
            }
            Log.i(TAG, "GetCurrenciesUseCase run: newItemRate in currenciesNew $oldItemRate")
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
        return CurrenciesDataModel(
            header = stringHelper.getString(R.string.header_rate),
            oldData = yesterday,
            newData = dataHelpers.getCurrentDayString(),
            currencyItems
        )
    }
}