package by.godevelopment.currencyappsample.data.datamodels

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RateCurrencyApiModel(
    @field:Json(name = "Cur_ID")
    val id: Int,
    @field:Json(name = "Cur_Abbreviation")
    val abbreviation: String,
    @field:Json(name = "Cur_Name")
    val name: String,
    @field:Json(name = "Cur_OfficialRate")
    val officialRate: Double,
    @field:Json(name = "Cur_Scale")
    val scale: Int,
    @field:Json(name = "Date")
    val date: String
)