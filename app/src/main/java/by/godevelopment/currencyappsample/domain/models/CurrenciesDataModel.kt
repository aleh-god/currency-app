package by.godevelopment.currencyappsample.domain.models

data class CurrenciesDataModel(
    val header: String = "",
    val oldData: String = "",
    val newData: String = "",
    val currencyItems: List<ItemCurrencyModel> = listOf()
)

