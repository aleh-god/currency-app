package by.godevelopment.currencyappsample.domain.models

data class SettingsDataModel(
    val header: String = "",
    val settingItems: List<ItemSettingsModel> = listOf()
)
