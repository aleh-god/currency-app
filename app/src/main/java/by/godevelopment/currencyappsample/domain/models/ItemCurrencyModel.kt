package by.godevelopment.currencyappsample.domain.models

data class ItemCurrencyModel(
    val curId: Int,
    val curName: String,
    val abbreviation: String,
    val scale: Int,
    val curValueOld: String,
    val curValueNew: String,
)