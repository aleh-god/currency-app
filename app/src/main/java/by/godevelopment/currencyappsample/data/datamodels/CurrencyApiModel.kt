package by.godevelopment.currencyappsample.data.datamodels

import com.google.gson.annotations.SerializedName

data class CurrencyApiModel(
    @SerializedName("Cur_Abbreviation")
    val abbreviation: String,
    @SerializedName("Cur_Code")
    val code: String,
    @SerializedName("Cur_DateEnd")
    val dateEnd: String,
    @SerializedName("Cur_DateStart")
    val dateStart: String,
    @SerializedName("Cur_ID")
    val curId: Int,
    @SerializedName("Cur_Name")
    val name: String,
    @SerializedName("Cur_Name_Bel")
    val nameBel: String,
    @SerializedName("Cur_Name_BelMulti")
    val nameBelMulti: String,
    @SerializedName("Cur_Name_Eng")
    val nameEng: String,
    @SerializedName("Cur_Name_EngMulti")
    val nameEngMulti: String,
    @SerializedName("Cur_NameMulti")
    val nameMulti: String,
    @SerializedName("Cur_ParentID")
    val parentID: Int,
    @SerializedName("Cur_Periodicity")
    val periodicity: Int,
    @SerializedName("Cur_QuotName")
    val quotName: String,
    @SerializedName("Cur_QuotName_Bel")
    val quotNameBel: String,
    @SerializedName("Cur_QuotName_Eng")
    val quotNameEng: String,
    @SerializedName("Cur_Scale")
    val scale: Int
)

// {"Cur_ID":190,"Cur_ParentID":190,"Cur_Code":"643","Cur_Abbreviation":"RUB","Cur_Name":"Российский рубль","Cur_Name_Bel":"Расійскі рубель","Cur_Name_Eng":"Russian Ruble","Cur_QuotName":"1 Российский рубль","Cur_QuotName_Bel":"1 Расійскі рубель","Cur_QuotName_Eng":"1 Russian Ruble","Cur_NameMulti":"","Cur_Name_BelMulti":"","Cur_Name_EngMulti":"","Cur_Scale":1,"Cur_Periodicity":0,"Cur_DateStart":"2003-01-01T00:00:00","Cur_DateEnd":"2016-06-30T00:00:00"}