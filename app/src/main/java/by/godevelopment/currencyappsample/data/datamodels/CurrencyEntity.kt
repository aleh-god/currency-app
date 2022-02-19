package by.godevelopment.currencyappsample.data.datamodels

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "currency_table")
data class CurrencyEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "abbreviation")
    val abbreviation: String,
    @ColumnInfo(name = "code")
    val code: String,
    @ColumnInfo(name = "date_record")
    val dateRecord: String,
    @ColumnInfo(name = "cur_id")
    val curId: Int,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "name_bel")
    val nameBel: String,
    @ColumnInfo(name = "name_bel_multi")
    val nameBelMulti: String,
    @ColumnInfo(name = "name_eng")
    val nameEng: String,
    @ColumnInfo(name = "name_eng_multi")
    val nameEngMulti: String,
    @ColumnInfo(name = "name_multi")
    val nameMulti: String,
    @ColumnInfo(name = "parent_id")
    val parentID: Int,
    @ColumnInfo(name = "periodicity")
    val periodicity: Int,
    @ColumnInfo(name = "quot_name")
    val quotName: String,
    @ColumnInfo(name = "quot_name_bel")
    val quotNameBel: String,
    @ColumnInfo(name = "quot_name_eng")
    val quotNameEng: String,
    @ColumnInfo(name = "scale")
    val scale: Int
)
