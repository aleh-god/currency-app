package by.godevelopment.currencyappsample.domain.models

data class ItemSettingsModel(
    val orderPosition: Int,
    val abbreviation: String,
    val scale: String,
    val curName: String,
    val isVisible: Boolean
)