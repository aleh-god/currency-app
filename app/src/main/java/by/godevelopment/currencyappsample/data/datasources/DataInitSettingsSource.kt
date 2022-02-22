package by.godevelopment.currencyappsample.data.datasources

import by.godevelopment.currencyappsample.data.datamodels.ItemSettingsEntity
import by.godevelopment.currencyappsample.domain.models.ItemCurrencyModel

object DataInitSettingsSource {
    val listCurrenciesVisibleByDefault  = listOf<ItemSettingsEntity>(
        ItemSettingsEntity(
            id = 0,
            curId = 431,
            abbreviation = "USD",
            name = "Доллар США",
            scale = 1,
            isVisible = true,
            orderPosition = 0
        ),
        ItemSettingsEntity(
            id = 0,
            curId = 451,
            abbreviation = "EUR",
            name = "Евро",
            scale = 1,
            isVisible = true,
            orderPosition = 1
        ),
        ItemSettingsEntity(
            id = 0,
            curId = 456,
            abbreviation = "RUB",
            name = "Российских рублей",
            scale = 100,
            isVisible = true,
            orderPosition = 2
        )
    )
}

// {"Cur_ID":431,"Date":"2022-02-20T00:00:00","Cur_Abbreviation":"USD","Cur_Scale":1,"Cur_Name":"Доллар США","Cur_OfficialRate":2.5740}
// {"Cur_ID":451,"Date":"2022-02-19T00:00:00","Cur_Abbreviation":"EUR","Cur_Scale":1,"Cur_Name":"Евро","Cur_OfficialRate":2.9235}
// {"Cur_ID":456,"Date":"2022-02-19T00:00:00","Cur_Abbreviation":"RUB","Cur_Scale":100,"Cur_Name":"Российских рублей","Cur_OfficialRate":3.3874}

// {"Cur_ID":19,"Cur_ParentID":19,"Cur_Code":"978","Cur_Abbreviation":"EUR","Cur_Name":"Евро","Cur_Name_Bel":"Еўра","Cur_Name_Eng":"EURO","Cur_QuotName":"1 Евро","Cur_QuotName_Bel":"1 Еўра","Cur_QuotName_Eng":"1 EURO","Cur_NameMulti":"","Cur_Name_BelMulti":"","Cur_Name_EngMulti":"","Cur_Scale":1,"Cur_Periodicity":0,"Cur_DateStart":"1999-01-01T00:00:00","Cur_DateEnd":"2016-06-30T00:00:00"}
// {"Cur_ID":292,"Cur_ParentID":19,"Cur_Code":"978","Cur_Abbreviation":"EUR","Cur_Name":"Евро","Cur_Name_Bel":"Еўра","Cur_Name_Eng":"Euro","Cur_QuotName":"1 Евро","Cur_QuotName_Bel":"1 Еўра","Cur_QuotName_Eng":"1 Euro","Cur_NameMulti":"Евро","Cur_Name_BelMulti":"Еўра","Cur_Name_EngMulti":"Euros","Cur_Scale":1,"Cur_Periodicity":0,"Cur_DateStart":"2016-07-01T00:00:00","Cur_DateEnd":"2021-07-08T00:00:00"}
// {"Cur_ID":451,"Cur_ParentID":19,"Cur_Code":"978","Cur_Abbreviation":"EUR","Cur_Name":"Евро","Cur_Name_Bel":"Еўра","Cur_Name_Eng":"Euro","Cur_QuotName":"1 Евро","Cur_QuotName_Bel":"1 Еўра","Cur_QuotName_Eng":"1 Euro","Cur_NameMulti":"Евро","Cur_Name_BelMulti":"Еўра","Cur_Name_EngMulti":"Euros","Cur_Scale":1,"Cur_Periodicity":0,"Cur_DateStart":"2021-07-09T00:00:00","Cur_DateEnd":"2050-01-01T00:00:00"}