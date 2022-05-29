package by.godevelopment.currencyappsample.presentation.ui.settings

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.godevelopment.currencyappsample.R
import by.godevelopment.currencyappsample.commons.INIT_VALUE_REFRESH_SETTINGS
import by.godevelopment.currencyappsample.commons.TAG
import by.godevelopment.currencyappsample.domain.helpers.StringHelper
import by.godevelopment.currencyappsample.domain.models.Event
import by.godevelopment.currencyappsample.domain.models.ItemSettingsModel
import by.godevelopment.currencyappsample.domain.usecase.LoadSettingsUseCase
import by.godevelopment.currencyappsample.domain.usecase.SaveSettingsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val saveSettingsUseCase: SaveSettingsUseCase,
    private val loadSettingsUseCase: LoadSettingsUseCase,
    private val stringHelper: StringHelper
) : ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _uiEvent  = MutableStateFlow<Event<String>?>(null)
    val uiEvent: StateFlow<Event<String>?> = _uiEvent.asStateFlow()

    private var suspendJob: Job? = null

    init {
        loadSettings(INIT_VALUE_REFRESH_SETTINGS)
    }

    fun loadSettings(refresh: Boolean) {
        Log.i(TAG, "loadSettings: $refresh")
        suspendJob?.cancel()
        suspendJob = viewModelScope.launch {
            loadSettingsUseCase(refresh)
                .onStart {
                    UiState(
                        isFetchingData = true,
                        header = stringHelper.getString(R.string.alert_loading)
                    )
                }
                .catch { exception ->
                    _uiState.value = UiState(
                        isFetchingData = false,
                        header = stringHelper.getString(R.string.alert_no_data)
                    )
                    _uiEvent.value = Event(exception.message)
                }
                .collect {
                    _uiState.value = UiState(
                        isFetchingData = false,
                        header = it.header,
                        settingsItems = it.settingItems
                    )
                    if (it.settingItems.size == 3) _uiEvent.value = Event(
                        stringHelper.getString(R.string.alert_reset_settings)
                    )
                }
        }
    }

    fun saveSettings(listItems: List<ItemSettingsModel>) {
        viewModelScope.launch {
            Log.i(TAG, "saveSettings: list size = ${listItems.size}")
            saveSettingsUseCase.run(listItems)
            _uiEvent.value = Event(
                stringHelper.getString(R.string.alert_save_settings)
            )
        }
    }

    fun changeVision(curId: Int, isVisible: Boolean) {
        val originUiState = uiState.value
        val originSettingsItems = originUiState.settingsItems.toMutableList()
        val originIndex = originSettingsItems.indexOfFirst {
            it.curId == curId
        }
        if (originIndex != -1) {
            val originItem = originSettingsItems[originIndex]
            val newItem = originItem.copy(isVisible = isVisible)
            originSettingsItems[originIndex] = newItem
            val newUiState = originUiState.copy(
                settingsItems = originSettingsItems
            )
            _uiState.value = newUiState
        }
    }

    fun saveOrderListItemsToItSelf(list: List<ItemSettingsModel>) {
        val reOrderedList = list.mapIndexed { index, itemSettingsModel ->
            itemSettingsModel.copy(
                orderPosition = index
            )
        }
        val originUiState = uiState.value
        val newUiState = originUiState.copy(
            settingsItems = reOrderedList
        )
        _uiState.value = newUiState
    }

    data class UiState(
        val isFetchingData: Boolean = false,
        val header: String = "",
        val settingsItems: List<ItemSettingsModel> = listOf(),
    )
}
