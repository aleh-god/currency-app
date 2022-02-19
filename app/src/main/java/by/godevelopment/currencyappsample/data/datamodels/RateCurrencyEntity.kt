package by.godevelopment.currencyappsample.data.datamodels

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rate_table")
data class RateCurrencyEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "abbreviation")
    val abbreviation: String,
    @ColumnInfo(name = "cur_id")
    val curId: Int,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "official_rate")
    val officialRate: Double,
    @ColumnInfo(name = "scale")
    val scale: Int,
    @ColumnInfo(name = "date_record")
    val dateRecord: String
)