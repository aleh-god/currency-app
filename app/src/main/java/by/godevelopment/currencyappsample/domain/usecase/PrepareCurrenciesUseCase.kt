package by.godevelopment.currencyappsample.domain.usecase

import by.godevelopment.currencyappsample.commons.INIT_VALUE_REFRESH_SETTINGS
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
                val currenciesDataModel = getAllRatesUseCase.run(EmptyParams)
                val currenciesList = currenciesDataModel.currencyItems
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
                            model.abbreviation == setting.abbreviation
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