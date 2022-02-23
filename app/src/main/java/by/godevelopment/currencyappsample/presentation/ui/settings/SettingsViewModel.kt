package by.godevelopment.currencyappsample.presentation.ui.settings

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.godevelopment.currencyappsample.commons.TAG
import by.godevelopment.currencyappsample.domain.models.ItemSettingsModel
import by.godevelopment.currencyappsample.domain.usecase.EmptyParams
import by.godevelopment.currencyappsample.domain.usecase.LoadSettingsUseCase
import by.godevelopment.currencyappsample.domain.usecase.SaveSettingsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val saveSettingsUseCase: SaveSettingsUseCase,
    private val loadSettingsUseCase: LoadSettingsUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState

    init {
        viewModelScope.launch {
            loadSettingsUseCase.execute(EmptyParams)
                .onStart {
                    UiState(
                        isFetchingArticles = true,
                        header = "Data is loading..."
                    )
                }
                .catch { exception ->
                    Log.i(TAG, "viewModelScope.launch.catch: ${exception.message}")
                    _uiState.value = UiState(
                        isFetchingArticles = false,
                        header = "${exception.message}"
                    )
                }
                .collect {
                    _uiState.value = UiState(
                        isFetchingArticles = false,
                        header = it.header,
                        settingsItems = it.settingItems
                    )
                }
        }
    }

    fun saveSettings(settings: List<ItemSettingsModel>) {
        viewModelScope.launch {
            saveSettingsUseCase.run(settings)
        }
    }

    data class UiState(
        val isFetchingArticles: Boolean = false,
        val header: String = "",
        val settingsItems: List<ItemSettingsModel> = listOf(),
        val userMessages: List<String> = listOf()
    )
}