package by.godevelopment.currencyappsample.data.datamodels

import com.google.gson.annotations.SerializedName

data class RateCurrencyApiModel(
    @SerializedName("Cur_Abbreviation")
    val abbreviation: String,
    @SerializedName("Cur_ID")
    val curId: Int,
    @SerializedName("Cur_Name")
    val name: String,
    @SerializedName("Cur_OfficialRate")
    val officialRate: Double,
    @SerializedName("Cur_Scale")
    val scale: Int,
    @SerializedName("Date")
    val date: String
)

// {"Cur_ID":431,"Date":"2022-02-17T00:00:00","Cur_Abbreviation":"USD","Cur_Scale":1,"Cur_Name":"Доллар США","Cur_OfficialRate":2.5604}
