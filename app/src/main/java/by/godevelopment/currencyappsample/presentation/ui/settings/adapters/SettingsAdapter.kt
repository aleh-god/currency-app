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

class SettingsAdapter : RecyclerView.Adapter<SettingsAdapter.ItemViewHolder>() {
    inner class ItemViewHolder(val binding: ItemSettingsBinding) : RecyclerView.ViewHolder(binding.root)

    private val diffCallback
            = object : DiffUtil.ItemCallback<ItemSettingsModel>() {
        override fun areContentsTheSame(
            oldItem: ItemSettingsModel,
            newItem: ItemSettingsModel
        ): Boolean {
            return oldItem.abbreviation == newItem.abbreviation
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
        set(value) { differ.submitList(value) }

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
            abbreviation.text = item.abbreviation
            curScale.text = item.scale
            curName.text = item.curName
            btnSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    Log.i(TAG, "setOnCheckedChangeListener: isChecked = true")
                } else
                {
                    Log.i(TAG, "setOnCheckedChangeListener: isChecked = false")
                }
            }
        }
    }

    override fun getItemCount(): Int = listItems.size
}