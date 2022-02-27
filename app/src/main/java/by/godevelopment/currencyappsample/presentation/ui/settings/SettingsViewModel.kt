package by.godevelopment.currencyappsample.presentation.ui.settings

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.godevelopment.currencyappsample.commons.INIT_VALUE_REFRESH_SETTINGS
import by.godevelopment.currencyappsample.commons.TAG
import by.godevelopment.currencyappsample.domain.models.ItemSettingsModel
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
        loadSettings(INIT_VALUE_REFRESH_SETTINGS)
    }

    fun loadSettings(refresh: Boolean) {
        Log.i(TAG, "loadSettings: $refresh")
        viewModelScope.launch {
            loadSettingsUseCase(refresh)
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

    fun saveSettings(listItems: List<ItemSettingsModel>) {
        viewModelScope.launch {
            saveSettingsUseCase.run(listItems)
        }
        // TODO "add alert"
    }

    fun changeVision(curId: Int, isChecked: Boolean) {
        Log.i(TAG, "SettingsViewModel changeVision: curId = $curId = $isChecked")
        val originUiState = uiState.value
        val originSettingsItems = originUiState.settingsItems.toMutableList()
        val originIndex = originSettingsItems.indexOfFirst {
            it.curId == curId
        }
        if (originIndex != -1) {
            val originItem = originSettingsItems[originIndex]
            val newItem = originItem.copy(isVisible = isChecked)
            originSettingsItems[originIndex] = newItem
            val newUiState = originUiState.copy(
                settingsItems = originSettingsItems
            )
            _uiState.value = newUiState
        }
    }

    data class UiState(
        val isFetchingArticles: Boolean = false,
        val header: String = "",
        val settingsItems: List<ItemSettingsModel> = listOf(),
        val userMessages: List<String> = listOf()
    )
}