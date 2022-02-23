package by.godevelopment.currencyappsample.presentation.ui.settings

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import by.godevelopment.currencyappsample.R
import by.godevelopment.currencyappsample.commons.TAG
import by.godevelopment.currencyappsample.databinding.SettingsFragmentBinding
import by.godevelopment.currencyappsample.domain.models.ItemSettingsModel
import by.godevelopment.currencyappsample.presentation.ui.settings.adapters.SettingsAdapter
import com.google.android.material.appbar.MaterialToolbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SettingsFragment : Fragment() {

    companion object {
        fun newInstance() = SettingsFragment()
    }

    private var _binding: SettingsFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SettingsViewModel by viewModels()

    private val helper: ItemTouchHelper = ItemTouchHelper(
        object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            0
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                dragged: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                val positionDragged = dragged.absoluteAdapterPosition
                val positionTarget = target.absoluteAdapterPosition
                val adapterRv = recyclerView.adapter as SettingsAdapter
                adapterRv.moveItem(positionDragged, positionTarget)
                return false
            }
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            }
        }
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SettingsFragmentBinding.inflate(inflater, container, false)
        setupUI()
        setupToolbar()
        return binding.root
    }

    private fun setupUI() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    binding.header.text = it.header
                    setupAdapter(it.settingsItems)
                }
            }
        }
    }

    private fun setupAdapter(listItems: List<ItemSettingsModel>) {
        binding.rvSettings.adapter = SettingsAdapter().apply {
            this.listItems = listItems
        }
        helper.attachToRecyclerView(binding.rvSettings)
    }

    private fun setupToolbar() {
        activity?.let {
            val toolbar = it.findViewById<MaterialToolbar>(R.id.toolbar)
            toolbar.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.save_settings -> {
                        val adapter = binding.rvSettings.adapter as SettingsAdapter
                        val settingsList = adapter.listItems
                        Log.i(TAG, "onOptionsItemSelected: save_list = ${settingsList.size}")
                        viewModel.saveSettings(settingsList)
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