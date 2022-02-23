package by.godevelopment.currencyappsample.presentation.ui.settings.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import by.godevelopment.currencyappsample.commons.TAG
import by.godevelopment.currencyappsample.databinding.ItemSettingsBinding
import by.godevelopment.currencyappsample.domain.models.ItemSettingsModel
import java.util.*

class SettingsAdapter : RecyclerView.Adapter<SettingsAdapter.ItemViewHolder>() {
    inner class ItemViewHolder(val binding: ItemSettingsBinding) : RecyclerView.ViewHolder(binding.root)

    private val diffCallback
            = object : DiffUtil.ItemCallback<ItemSettingsModel>() {
        override fun areContentsTheSame(
            oldItem: ItemSettingsModel,
            newItem: ItemSettingsModel
        ): Boolean {
            return oldItem.curId == newItem.curId
        }

        override fun areItemsTheSame(
            oldItem: ItemSettingsModel,
            newItem: ItemSettingsModel
        ): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    var listItems: List<ItemSettingsModel>
        get() = differ.currentList
        set(value) {
            differ.submitList(value)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            ItemSettingsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = listItems[position]
        holder.binding.apply {
            Log.i(TAG, "onBindViewHolder: ${item.abbreviation} pos = $position check = ${item.isVisible}")
            btnSwitch.setOnCheckedChangeListener(null)
            abbreviation.text = item.abbreviation
            curScale.text = item.scale.toString()
            curName.text = item.curName
            btnSwitch.isChecked = item.isVisible
            btnSwitch.setOnCheckedChangeListener { _, isChecked ->
                changeVisibleStatus(position, isChecked)
                Log.i(TAG, "setOnCheckedChangeListener: ${item.abbreviation} pos = $position check = $isChecked")
            }
        }
    }

    override fun getItemCount(): Int = listItems.size

    private fun changeVisibleStatus(position: Int, isChecked: Boolean) {
        val newList = listItems.toMutableList()
        val originItem = newList[position]
        val newItem = originItem.copy(isVisible = isChecked)
        newList[position] = newItem
        listItems = newList
    }

    fun moveItem(fromPosition: Int, toPosition: Int) {
        Log.i(TAG, "moveItem: fromPosition = $fromPosition toPosition = $toPosition")
        val newList = listItems.toMutableList()
        Collections.swap(newList, fromPosition, toPosition)
        listItems = newList
        notifyItemMoved(fromPosition, toPosition)
    }
}