package by.godevelopment.currencyappsample.presentation.ui.currencieslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import by.godevelopment.currencyappsample.R
import by.godevelopment.currencyappsample.databinding.CurrenciesListFragmentBinding
import by.godevelopment.currencyappsample.domain.models.ItemCurrencyModel
import by.godevelopment.currencyappsample.presentation.ui.currencieslist.adapters.ListAdapter
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.snackbar.Snackbar
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
        setupToolbar()
        setupUI()
        setupEvent()
        return binding.root
    }

    private fun setupUI() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    binding.progressBar.visibility =
                        if (uiState.isFetchingData) View.VISIBLE else View.INVISIBLE
                    binding.header.text = uiState.header
                    binding.dateOld.text = uiState.oldData
                    binding.dateNew.text = uiState.newData
                    setupAdapter(uiState.CurrencyItems)
                }
            }
        }
    }

    private fun setupEvent() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiEvent.collect { event ->
                    event?.let {
                        it.get()?.let { message ->
                            Snackbar
                                .make(binding.root, message, Snackbar.LENGTH_INDEFINITE)
                                .setAction(getString(R.string.text_snackbar_reload))
                                { viewModel.loadCurrencies() }
                                .show()
                        }
                    }
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