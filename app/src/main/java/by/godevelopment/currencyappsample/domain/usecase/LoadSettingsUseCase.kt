package by.godevelopment.currencyappsample.domain.usecase

import by.godevelopment.currencyappsample.R
import by.godevelopment.currencyappsample.domain.helpers.StringHelper
import by.godevelopment.currencyappsample.domain.models.ItemSettingsModel
import by.godevelopment.currencyappsample.domain.models.SettingsDataModel
import by.godevelopment.currencyappsample.domain.repositories.SettingsRep
import javax.inject.Inject

class LoadSettingsUseCase @Inject constructor(
    private val settingsRep: SettingsRep,
    private val stringHelper: StringHelper
) : BaseUseCase<SettingsDataModel, EmptyParams>() {
    override suspend fun run(params: EmptyParams): SettingsDataModel {
        return SettingsDataModel(
            header = stringHelper.getString(R.string.header_settings),
            settingItems = settingsRep.loadSettings().map {
                ItemSettingsModel(
                    curId = it.curId,
                    curName = it.name,
                    abbreviation = it.abbreviation,
                    scale = it.scale,
                    isVisible = it.isVisible,
                    orderPosition = it.orderPosition
                )
            }
        )
    }
}
