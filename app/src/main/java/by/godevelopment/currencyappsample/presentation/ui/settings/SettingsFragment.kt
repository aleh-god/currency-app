package by.godevelopment.currencyappsample.presentation.ui.settings

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        setupToolbar()
        setupRv()
        setupUI()
        return binding.root
    }

    private fun setupRv() {
        Log.i(TAG, "SettingsFragment setupRv")
        val callback: (Int, Boolean) -> Unit = { pos, vis -> viewModel.changeVision(pos, vis) }
        binding.rvSettings.adapter = SettingsAdapter(callback)
        helper.attachToRecyclerView(binding.rvSettings)
    }

    private fun setupUI() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    binding.header.text = uiState.header
                    Log.i(TAG, "SettingsFragment setupUI: ${uiState.settingsItems.size}")
                    val adapter = binding.rvSettings.adapter as SettingsAdapter
                    adapter.listItems = uiState.settingsItems
                }
            }
        }
    }

    private fun setupToolbar() {
        activity?.let {
            val toolbar = it.findViewById<MaterialToolbar>(R.id.toolbar)
            toolbar.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.save_settings -> {
                        val settingsList = viewModel.uiState.value.settingsItems
                        Log.i(TAG, "onOptionsItemSelected: save_list = ${settingsList.size}")
                        viewModel.saveSettings(settingsList)
                        true
                    }
                    R.id.refresh_settings -> {
                        Log.i(TAG, "onOptionsItemSelected: refresh_list ")
                        viewModel.loadSettings(true)
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