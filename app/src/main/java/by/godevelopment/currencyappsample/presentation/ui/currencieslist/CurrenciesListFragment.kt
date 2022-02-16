package by.godevelopment.currencyappsample.presentation.ui.currencieslist

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import by.godevelopment.currencyappsample.databinding.CurrenciesListFragmentBinding
import by.godevelopment.currencyappsample.domain.models.ItemCurrencyModel
import by.godevelopment.currencyappsample.presentation.ui.currencieslist.adapters.ListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

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
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    binding.header.text = it.header
                    binding.dateOld.text = it.oldData
                    binding.dateNew.text = it.newData
                    setupAdapter(it.CurrencyItems)
                }
            }
        }
    }

    private fun setupAdapter(listItems: List<ItemCurrencyModel>) {
        binding.rvCurrencies.adapter = ListAdapter().apply {
            this.listItems = listItems
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}