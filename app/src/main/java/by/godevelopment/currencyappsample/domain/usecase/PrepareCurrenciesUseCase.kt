package by.godevelopment.currencyappsample.domain.usecase

import android.util.Log
import by.godevelopment.currencyappsample.commons.INIT_VALUE_REFRESH_SETTINGS
import by.godevelopment.currencyappsample.commons.TAG
import by.godevelopment.currencyappsample.domain.models.CurrenciesDataModel
import by.godevelopment.currencyappsample.domain.repositories.SettingsRep
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PrepareCurrenciesUseCase @Inject constructor(
    private val settingsRep: SettingsRep,
    private val getAllRatesUseCase: GetAllRatesUseCase
) {
    suspend operator fun invoke(): Flow<CurrenciesDataModel> =
        settingsRep.loadSettings(INIT_VALUE_REFRESH_SETTINGS)
            .map { settings ->
                Log.i(TAG, "PrepareCurrenciesUseCase: settings size = ${settings.size}")
                val currenciesDataModel = getAllRatesUseCase.run(EmptyParams)
                val currenciesList = currenciesDataModel.currencyItems
                Log.i(TAG, "PrepareCurrenciesUseCase: currenciesList size = ${currenciesList.size}")
                val orderMap = settings
                    .sortedBy { it.orderPosition }
                    .associate {
                        it.curId to it.orderPosition
                    }
                val resultList = settings
                    .filter {
                        it.isVisible
                    }
                    .flatMap { setting ->
                        currenciesList.filter { model ->
                            model.curId == setting.curId
                        }
                    }
                    .sortedBy {
                        orderMap[it.curId]
                    }
                currenciesDataModel.copy(
                    currencyItems = resultList
                )
            }
}