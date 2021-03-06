package by.godevelopment.currencyappsample.domain.usecase

import android.util.Log
import by.godevelopment.currencyappsample.commons.TAG
import by.godevelopment.currencyappsample.data.datamodels.ItemSettingsEntity
import by.godevelopment.currencyappsample.domain.models.ItemSettingsModel
import by.godevelopment.currencyappsample.domain.repositories.SettingsRep
import javax.inject.Inject

class SaveSettingsUseCase @Inject constructor(
    private val settingsRep: SettingsRep
) : BaseUseCase<Unit, List<ItemSettingsModel>>() {
    override suspend fun run(params: List<ItemSettingsModel>) {
        Log.i(TAG, "SaveSettingsUseCase: list = ${params.size}")
        settingsRep.saveSettings(
            params.map {
                ItemSettingsEntity(
                    id = it.id,
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
