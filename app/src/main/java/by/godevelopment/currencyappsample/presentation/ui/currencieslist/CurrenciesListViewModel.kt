package by.godevelopment.currencyappsample.presentation.ui.currencieslist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.godevelopment.currencyappsample.R
import by.godevelopment.currencyappsample.commons.TAG
import by.godevelopment.currencyappsample.domain.helpers.StringHelper
import by.godevelopment.currencyappsample.domain.models.Event
import by.godevelopment.currencyappsample.domain.models.ItemCurrencyModel
import by.godevelopment.currencyappsample.domain.usecase.PrepareCurrenciesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrenciesListViewModel @Inject constructor(
    private val prepareCurrenciesUseCase: PrepareCurrenciesUseCase,
    private val stringHelper: StringHelper
) : ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState

    private val _uiEvent  = MutableStateFlow<Event<String>?>(null)
    val uiEvent: StateFlow<Event<String>?> = _uiEvent

    init {
        loadCurrencies()
    }

    fun loadCurrencies() {
        viewModelScope.launch {
            prepareCurrenciesUseCase()
                .onStart {
                    _uiState.value = UiState(
                        isFetchingData = true,
                        header = stringHelper.getString(R.string.alert_loading)
                    )
                }
                .catch { exception ->
                    Log.i(TAG, "ViewModel loadCurrencies: .catch ${exception.message}")
                    _uiState.value = UiState(
                        isFetchingData = false,
                        header = stringHelper.getString(R.string.alert_no_data)
                    )
                    _uiEvent.value = Event(
                        "${exception.message}"
                    )
                }
                .collect {
                    _uiState.value = UiState(
                        isFetchingData = false,
                        header = stringHelper.getString(R.string.header_rate),
                        oldData = it.oldData,
                        newData = it.newData,
                        CurrencyItems = it.currencyItems
                    )
                }
        }
    }

    data class UiState(
        val isFetchingData: Boolean = false,
        val header: String = "",
        val oldData: String = "",
        val newData: String = "",
        val CurrencyItems: List<ItemCurrencyModel> = listOf()
    )
}
