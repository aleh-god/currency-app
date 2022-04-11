package by.godevelopment.currencyappsample.presentation.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.ACTION_STATE_DRAG
import androidx.recyclerview.widget.RecyclerView
import by.godevelopment.currencyappsample.R
import by.godevelopment.currencyappsample.databinding.SettingsFragmentBinding
import by.godevelopment.currencyappsample.presentation.ui.settings.adapters.SettingsAdapter
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.snackbar.Snackbar
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
    private val adapter: SettingsAdapter by lazy {
        SettingsAdapter {
                position, vision -> viewModel.changeVision(position, vision)
        }
    }

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

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {}

            override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
                super.onSelectedChanged(viewHolder, actionState)
                if(actionState == ACTION_STATE_DRAG) {
                    viewHolder?.itemView?.scaleY = 1.3f
                    viewHolder?.itemView?.alpha = 0.7f
                }
            }

            override fun clearView(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ) {
                viewHolder.itemView.scaleY = 1.0f
                viewHolder.itemView.alpha = 1.0f
                val oldList = adapter.listItems
                viewModel.saveOrderListItemsToItSelf(oldList)
                super.clearView(recyclerView, viewHolder)
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
        setupEvent()
        return binding.root
    }

    private fun setupRv() {
        binding.rvSettings.adapter = adapter
        helper.attachToRecyclerView(binding.rvSettings)
    }

    private fun setupUI() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    binding.progressBar.visibility =
                        if (uiState.isFetchingData) View.VISIBLE else View.INVISIBLE
                    binding.header.text = uiState.header
                    adapter.listItems = uiState.settingsItems
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
                                .setAction(getString(R.string.text_snackbar_ok))
                                {  }
                                .show()
                        }
                    }
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
                        val listItems = adapter.listItems
                        viewModel.saveSettings(listItems)
                        true
                    }
                    R.id.refresh_settings -> {
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