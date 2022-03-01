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

class SettingsAdapter (
    private val changeVisibleCallBack: (Int, Boolean) -> Unit
        ) : RecyclerView.Adapter<SettingsAdapter.ItemViewHolder>() {
    inner class ItemViewHolder(
        private val binding: ItemSettingsBinding
        ) : RecyclerView.ViewHolder(binding.root) {
            fun bindView(item: ItemSettingsModel) {
                binding.apply {
                    Log.i(TAG, "onBindViewHolder: ${item.abbreviation} check = ${item.orderPosition}")
                    btnSwitch.setOnCheckedChangeListener(null)
                    abbreviation.text = item.abbreviation
                    curScale.text = item.scale.toString()
                    curName.text = item.curName
                    btnSwitch.isChecked = item.isVisible
                    btnSwitch.setOnCheckedChangeListener { _, isChecked ->
                        changeVisibleCallBack(item.curId, isChecked)
                        Log.i(TAG, "setOnCheckedChangeListener: ${item.orderPosition} = ${item.abbreviation} check = $isChecked")
                    }
                }
            }
        }

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
        holder.bindView(item)
    }

    override fun getItemCount(): Int = listItems.size

    fun moveItem(fromPosition: Int, toPosition: Int) {
        Log.i(TAG, "moveItem: fromPosition = $fromPosition toPosition = $toPosition")
        val newList = listItems.toMutableList()
        val fromItem = newList[fromPosition]
        newList.removeAt(fromPosition)
        if (toPosition < fromPosition) {
            newList.add(toPosition + 1 , fromItem)
        } else {
            newList.add(toPosition - 1, fromItem)
        }
        differ.submitList(newList)
    }
}