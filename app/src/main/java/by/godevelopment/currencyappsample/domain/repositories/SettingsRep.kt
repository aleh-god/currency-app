package by.godevelopment.currencyappsample.domain.repositories

import by.godevelopment.currencyappsample.data.datamodels.ItemSettingsEntity

interface SettingsRep {
    suspend fun getSettings(): List<ItemSettingsEntity>
    suspend fun saveSettings(settings: List<ItemSettingsEntity>)
}