package by.godevelopment.currencyappsample.domain.repositories

import by.godevelopment.currencyappsample.data.datamodels.ItemSettingsEntity

interface SettingsRep {
    suspend fun loadSettings(): List<ItemSettingsEntity>
    suspend fun saveSettings(settings: List<ItemSettingsEntity>)
}