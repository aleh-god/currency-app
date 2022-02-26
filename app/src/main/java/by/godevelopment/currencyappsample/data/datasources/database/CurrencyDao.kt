package by.godevelopment.currencyappsample.data.datasources.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import by.godevelopment.currencyappsample.data.datamodels.CurrencyEntity

@Dao
interface CurrencyDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllCurrencies(listRates: List<CurrencyEntity>): List<Long>

    @Query("SELECT * FROM currency_table ORDER BY id DESC")
    suspend fun getAllCurrencies(): List<CurrencyEntity>

    @Query("DELETE FROM currency_table")
    suspend fun deleteAll(): Int
}