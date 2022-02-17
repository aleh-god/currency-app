package by.godevelopment.currencyappsample.presentation.ui.currencieslist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.godevelopment.currencyappsample.commons.TAG
import by.godevelopment.currencyappsample.data.datasources.DataTestSource
import by.godevelopment.currencyappsample.domain.models.ItemCurrencyModel
import by.godevelopment.currencyappsample.domain.usecase.EmptyParams
import by.godevelopment.currencyappsample.domain.usecase.GetCurrenciesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrenciesListViewModel @Inject constructor(
    private val getCurrenciesUseCase: GetCurrenciesUseCase

) : ViewModel() {
    val list = DataTestSource.listData

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState

    init {
        viewModelScope.launch {
            getCurrenciesUseCase.execute(EmptyParams)
                .onStart {
                    _uiState.value = UiState(
                        isFetchingArticles = false,
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
                        isFetchingArticles = true,
                        header = it.header,
                        oldData = it.oldData,
                        newData = it.newData,
                        CurrencyItems = it.CurrencyItems
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
