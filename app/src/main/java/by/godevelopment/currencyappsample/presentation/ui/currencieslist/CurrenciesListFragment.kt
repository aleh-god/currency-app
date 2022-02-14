package by.godevelopment.currencyappsample.presentation.ui.currencieslist

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import by.godevelopment.currencyappsample.databinding.CurrenciesListFragmentBinding
import by.godevelopment.currencyappsample.presentation.ui.currencieslist.adapters.ListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CurrenciesListFragment : Fragment() {

    companion object {
        fun newInstance() = CurrenciesListFragment()
    }

    private var _binding: CurrenciesListFragmentBinding ? = null
    private val binding get() = _binding!!

    private val viewModel: CurrenciesListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = CurrenciesListFragmentBinding.inflate(inflater, container, false)
        setupUI()
        return binding.root
    }

    private fun setupUI() {
        setupAdapter()
        binding.header.text = "Курс беллорусского рубля:"
        binding.dateOld.text = "01-01-2022"
        binding.dateNew.text = "31-01-2022"
    }

    private fun setupAdapter() {
        binding.rvCurrencies.adapter = ListAdapter().apply {
            this.listItems = viewModel.list
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}