package by.godevelopment.currencyappsample.domain.usecase

import by.godevelopment.currencyappsample.data.datamodels.ItemSettingsEntity
import by.godevelopment.currencyappsample.domain.models.ItemSettingsModel
import by.godevelopment.currencyappsample.domain.repositories.SettingsRep
import javax.inject.Inject

class SaveSettingsUseCase @Inject constructor(
    private val settingsRep: SettingsRep
) : BaseUseCase<Unit, List<ItemSettingsModel>>() {
    override suspend fun run(params: List<ItemSettingsModel>) {
        settingsRep.saveSettings(
            params.map {
                ItemSettingsEntity(
                    id = 0,
                    curId = it.curId,
                    abbreviation = it.abbreviation,
                    name = it.curName,
                    scale = it.scale,
                    isVisible = it.isVisible,
                    orderPosition = it.orderPosition
                )
            }
        )
    }
}
