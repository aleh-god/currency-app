package by.godevelopment.currencyappsample.data.model

data class Currency(
    val Cur_Abbreviation: String,
    val Cur_Code: String,
    val Cur_DateEnd: String,
    val Cur_DateStart: String,
    val Cur_ID: Int,
    val Cur_Name: String,
    val Cur_NameMulti: String,
    val Cur_Name_Bel: String,
    val Cur_Name_BelMulti: String,
    val Cur_Name_Eng: String,
    val Cur_Name_EngMulti: String,
    val Cur_ParentID: Int,
    val Cur_Periodicity: Int,
    val Cur_QuotName: String,
    val Cur_QuotName_Bel: String,
    val Cur_QuotName_Eng: String,
    val Cur_Scale: Int
)

// {"Cur_ID":190,"Cur_ParentID":190,"Cur_Code":"643","Cur_Abbreviation":"RUB","Cur_Name":"Российский рубль","Cur_Name_Bel":"Расійскі рубель","Cur_Name_Eng":"Russian Ruble","Cur_QuotName":"1 Российский рубль","Cur_QuotName_Bel":"1 Расійскі рубель","Cur_QuotName_Eng":"1 Russian Ruble","Cur_NameMulti":"","Cur_Name_BelMulti":"","Cur_Name_EngMulti":"","Cur_Scale":1,"Cur_Periodicity":0,"Cur_DateStart":"2003-01-01T00:00:00","Cur_DateEnd":"2016-06-30T00:00:00"}