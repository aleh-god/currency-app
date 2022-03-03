package by.godevelopment.currencyappsample.domain.usecase

import android.util.Log
import by.godevelopment.currencyappsample.R
import by.godevelopment.currencyappsample.commons.TAG
import by.godevelopment.currencyappsample.domain.helpers.StringHelper
import by.godevelopment.currencyappsample.domain.models.ItemSettingsModel
import by.godevelopment.currencyappsample.domain.models.SettingsDataModel
import by.godevelopment.currencyappsample.domain.repositories.SettingsRep
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LoadSettingsUseCase @Inject constructor(
    private val settingsRep: SettingsRep,
    private val stringHelper: StringHelper
) {
    suspend operator fun invoke(reset: Boolean): Flow<SettingsDataModel> =
        settingsRep.loadSettings(reset)
            .map { list ->
                Log.i(TAG, "LoadSettingsUseCase reset = $reset")

                SettingsDataModel(
                    header = if (list.size > 3) {
                        stringHelper.getString(R.string.header_settings)
                    } else {
                        stringHelper.getString(R.string.header_settings_init)
                    },
                    settingItems = list
                        .map { it ->
                            ItemSettingsModel(
                                id = it.id,
                                curId = it.curId,
                                curName = it.name,
                                abbreviation = it.abbreviation,
                                scale = it.scale,
                                isVisible = it.isVisible,
                                orderPosition = it.orderPosition
                            )
                        }
                        .sortedBy {
                            it.orderPosition
                        }
                )
            }
}
