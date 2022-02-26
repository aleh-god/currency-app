package by.godevelopment.currencyappsample.domain.usecase

import by.godevelopment.currencyappsample.commons.INIT_VALUE_REFRESH_SETTINGS
import by.godevelopment.currencyappsample.domain.models.CurrenciesDataModel
import by.godevelopment.currencyappsample.domain.repositories.SettingsRep
import javax.inject.Inject

class SortCurrenciesUseCase @Inject constructor(
    private val settingsRep: SettingsRep,
    private val getCurrenciesUseCase: GetCurrenciesUseCase
): BaseUseCase<CurrenciesDataModel, EmptyParams>() {
    override suspend fun run(params: EmptyParams): CurrenciesDataModel {
        val settings = settingsRep.loadSettings(INIT_VALUE_REFRESH_SETTINGS)
        val currenciesDataModel = getCurrenciesUseCase.run(EmptyParams)
        val currenciesList = currenciesDataModel.currencyItems
        val resultList = settings
            .filter {
                it.isVisible
            }
            .flatMap { setting ->
            currenciesList.filter { model ->
                model.abbreviation == setting.abbreviation
            }
        }
        return currenciesDataModel.copy(
            currencyItems = resultList
        )
    }
}