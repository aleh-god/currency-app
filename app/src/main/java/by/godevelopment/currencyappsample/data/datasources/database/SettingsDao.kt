package by.godevelopment.currencyappsample.data.datasources.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import by.godevelopment.currencyappsample.data.datamodels.ItemSettingsEntity

@Dao
interface SettingsDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllSettings(listSettings: List<ItemSettingsEntity>)

    @Query("SELECT * FROM settings_table")
    suspend fun getAllSettings(): List<ItemSettingsEntity>

    @Query("DELETE FROM settings_table")
    suspend fun deleteAll()
}