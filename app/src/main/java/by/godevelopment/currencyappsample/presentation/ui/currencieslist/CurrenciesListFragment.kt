package by.godevelopment.currencyappsample.presentation.ui.currencieslist

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import by.godevelopment.currencyappsample.R
import by.godevelopment.currencyappsample.commons.TAG
import by.godevelopment.currencyappsample.databinding.CurrenciesListFragmentBinding
import by.godevelopment.currencyappsample.domain.models.ItemCurrencyModel
import by.godevelopment.currencyappsample.presentation.ui.currencieslist.adapters.ListAdapter
import com.google.android.material.appbar.MaterialToolbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CurrenciesListFragment : Fragment() {

    companion object {
        fun newInstance() = CurrenciesListFragment()
    }

    private var _binding: CurrenciesListFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CurrenciesListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = CurrenciesListFragmentBinding.inflate(inflater, container, false)
        setupUI()
        setupToolbar()
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

    private fun setupToolbar() {
        activity?.let {
            val toolbar = it.findViewById<MaterialToolbar>(R.id.toolbar)
            toolbar.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.refresh_settings -> {
                        Log.i(TAG, "onOptionsItemSelected: refresh_list ")
                        viewModel.loadCurrencies()
                        true
                    }
                    else -> false
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}