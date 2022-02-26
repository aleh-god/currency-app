package by.godevelopment.currencyappsample.domain.repositories

import by.godevelopment.currencyappsample.data.datamodels.ItemSettingsEntity
import kotlinx.coroutines.flow.Flow

interface SettingsRep {
    suspend fun saveSettings(settings: List<ItemSettingsEntity>)
    suspend fun loadSettings(reset: Boolean): Flow<List<ItemSettingsEntity>>
}