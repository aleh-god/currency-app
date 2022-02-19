package by.godevelopment.currencyappsample.data.datasources.database

import androidx.room.Database
import androidx.room.RoomDatabase
import by.godevelopment.currencyappsample.data.datamodels.CurrencyEntity
import by.godevelopment.currencyappsample.data.datamodels.ItemSettingsEntity
import by.godevelopment.currencyappsample.data.datamodels.RateCurrencyEntity

@Database(entities = [CurrencyEntity::class, RateCurrencyEntity::class, ItemSettingsEntity::class], version = 1, exportSchema = false)
abstract class CurrencyDatabase : RoomDatabase() {
    abstract fun getRateCurrencyDao(): RateCurrencyDao
    abstract fun getCurrencyDao(): CurrencyDao
    abstract fun getSettingsDao(): SettingsDao
}