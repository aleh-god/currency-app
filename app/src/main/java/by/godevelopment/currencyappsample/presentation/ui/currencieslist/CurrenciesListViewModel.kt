package by.godevelopment.currencyappsample.presentation.ui.currencieslist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.godevelopment.currencyappsample.commons.TAG
import by.godevelopment.currencyappsample.domain.models.ItemCurrencyModel
import by.godevelopment.currencyappsample.domain.usecase.EmptyParams
import by.godevelopment.currencyappsample.domain.usecase.SortCurrenciesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrenciesListViewModel @Inject constructor(
    private val sortCurrenciesUseCase: SortCurrenciesUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState

    init {
        loadCurrencies()
    }

    fun loadCurrencies() {
        viewModelScope.launch {
            sortCurrenciesUseCase()
                .onStart {
                    _uiState.value = UiState(
                        isFetchingArticles = true,
                        header = "Data is loading..."
                    )
                }
                .catch { exception ->
                    Log.i(TAG, "viewModelScope.launch.catch: ${exception.message}")
                    _uiState.value = UiState(
                        isFetchingArticles = false,
                        header = "${exception.message}",
                        oldData = "Error!!!",
                        newData = "Error!!!"
                    )
                }
                .collect {
                    _uiState.value = UiState(
                        isFetchingArticles = false,
                        header = it.header,
                        oldData = it.oldData,
                        newData = it.newData,
                        CurrencyItems = it.currencyItems
                    )
                }
        }
    }

    data class UiState(
        val isFetchingArticles: Boolean = false,
        val header: String = "",
        val oldData: String = "",
        val newData: String = "",
        val CurrencyItems: List<ItemCurrencyModel> = listOf(),
        val userMessages: List<String> = listOf()
    )
}
