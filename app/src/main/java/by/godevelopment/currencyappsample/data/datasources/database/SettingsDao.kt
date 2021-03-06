package by.godevelopment.currencyappsample.data.datasources.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import by.godevelopment.currencyappsample.data.datamodels.ItemSettingsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SettingsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllSettings(listSettings: List<ItemSettingsEntity>): List<Long>

    @Query("SELECT * FROM settings_table")
    fun getAllSettings(): Flow<List<ItemSettingsEntity>>

    @Query("DELETE FROM settings_table")
    suspend fun deleteAll()
}