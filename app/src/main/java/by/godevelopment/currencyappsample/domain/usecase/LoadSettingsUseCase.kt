package by.godevelopment.currencyappsample.domain.usecase

import android.util.Log
import by.godevelopment.currencyappsample.R
import by.godevelopment.currencyappsample.commons.TAG
import by.godevelopment.currencyappsample.domain.helpers.StringHelper
import by.godevelopment.currencyappsample.domain.models.ItemSettingsModel
import by.godevelopment.currencyappsample.domain.models.SettingsDataModel
import by.godevelopment.currencyappsample.domain.repositories.SettingsRep
import javax.inject.Inject

class LoadSettingsUseCase @Inject constructor(
    private val settingsRep: SettingsRep,
    private val stringHelper: StringHelper
) : BaseUseCase<SettingsDataModel, Boolean>() {
    override suspend fun run(refresh: Boolean): SettingsDataModel {
        Log.i(TAG, "LoadSettingsUseCase $refresh")
        return SettingsDataModel(
            header = stringHelper.getString(R.string.header_settings),
            settingItems = settingsRep.loadSettings(refresh).map {
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
