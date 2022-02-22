package by.godevelopment.currencyappsample.data.datamodels

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "settings_table")
data class ItemSettingsEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "cur_id")
    val curId: Int,
    @ColumnInfo(name = "abbreviation")
    val abbreviation: String,
    @ColumnInfo(name = "scale")
    val scale: Int,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "visible")
    val isVisible: Boolean,
    @ColumnInfo(name = "order")
    val orderPosition: Int
)
