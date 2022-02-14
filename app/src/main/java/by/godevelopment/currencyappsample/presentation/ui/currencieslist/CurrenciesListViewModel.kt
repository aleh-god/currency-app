package by.godevelopment.currencyappsample.presentation.ui.currencieslist

import androidx.lifecycle.ViewModel
import by.godevelopment.currencyappsample.data.model.DataTestSource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CurrenciesListViewModel @Inject constructor(

) : ViewModel() {
    val list = DataTestSource.listData
}