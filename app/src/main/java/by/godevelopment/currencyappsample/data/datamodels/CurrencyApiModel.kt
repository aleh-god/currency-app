package by.godevelopment.currencyappsample.data.datamodels

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CurrencyApiModel(
    @field:Json(name = "Cur_Abbreviation")
    val abbreviation: String,
    @field:Json(name = "Cur_Code")
    val code: String,
    @field:Json(name = "Cur_DateEnd")
    val dateEnd: String,
    @field:Json(name = "Cur_DateStart")
    val dateStart: String,
    @field:Json(name = "Cur_ID")
    val id: Int,
    @field:Json(name = "Cur_Name")
    val name: String,
    @field:Json(name = "Cur_NameMulti")
    val nameMulti: String,
    @field:Json(name = "Cur_Name_Bel")
    val nameBel: String,
    @field:Json(name = "Cur_Name_BelMulti")
    val nameBelMulti: String,
    @field:Json(name = "Cur_Name_Eng")
    val nameEng: String,
    @field:Json(name = "Cur_Name_EngMulti")
    val nameEngMulti: String,
    @field:Json(name = "Cur_ParentID")
    val parentID: Int,
    @field:Json(name = "Cur_Periodicity")
    val periodicity: Int,
    @field:Json(name = "Cur_QuotName")
    val quotName: String,
    @field:Json(name = "Cur_QuotName_Bel")
    val quotNameBel: String,
    @field:Json(name = "Cur_QuotName_Eng")
    val quotNameEng: String,
    @field:Json(name = "Cur_Scale")
    val scale: Int
)

// {"Cur_ID":190,"Cur_ParentID":190,"Cur_Code":"643","Cur_Abbreviation":"RUB","Cur_Name":"Российский рубль","Cur_Name_Bel":"Расійскі рубель","Cur_Name_Eng":"Russian Ruble","Cur_QuotName":"1 Российский рубль","Cur_QuotName_Bel":"1 Расійскі рубель","Cur_QuotName_Eng":"1 Russian Ruble","Cur_NameMulti":"","Cur_Name_BelMulti":"","Cur_Name_EngMulti":"","Cur_Scale":1,"Cur_Periodicity":0,"Cur_DateStart":"2003-01-01T00:00:00","Cur_DateEnd":"2016-06-30T00:00:00"}