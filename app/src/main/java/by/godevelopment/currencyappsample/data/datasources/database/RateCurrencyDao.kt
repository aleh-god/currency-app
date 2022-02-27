package by.godevelopment.currencyappsample.data.datasources.database

import androidx.room.*
import by.godevelopment.currencyappsample.data.datamodels.RateCurrencyEntity

@Dao
interface RateCurrencyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllRates(listRates: List<RateCurrencyEntity>): List<Long>

    @Query("SELECT * FROM rate_table ORDER BY id DESC")
    suspend fun getAllRates(): List<RateCurrencyEntity>

    @Query("SELECT * FROM rate_table WHERE date_record = :key")
    suspend fun getAllRatesByDate(key: String): List<RateCurrencyEntity>

    @Query("DELETE FROM rate_table")
    suspend fun deleteAll()

    @Query("DELETE FROM rate_table WHERE date_record = :key")
    suspend fun deleteAllByDate(key: String): Int
}