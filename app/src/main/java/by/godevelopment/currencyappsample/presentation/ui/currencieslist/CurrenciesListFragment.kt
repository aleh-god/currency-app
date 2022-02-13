package by.godevelopment.currencyappsample.presentation.ui.currencieslist

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import by.godevelopment.currencyappsample.databinding.CurrenciesListFragmentBinding
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
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel
        setupUI()
    }

    private fun setupUI() {
        setupAdapter()
    }

    private fun setupAdapter() {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}