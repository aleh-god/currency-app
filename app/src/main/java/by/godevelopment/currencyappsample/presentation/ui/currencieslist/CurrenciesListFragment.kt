package by.godevelopment.currencyappsample.presentation.ui.currencieslist

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import by.godevelopment.currencyappsample.R

class CurrenciesListFragment : Fragment() {

    companion object {
        fun newInstance() = CurrenciesListFragment()
    }

    private lateinit var viewModel: CurrenciesListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.currencies_list_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CurrenciesListViewModel::class.java)
        // TODO: Use the ViewModel
    }

}