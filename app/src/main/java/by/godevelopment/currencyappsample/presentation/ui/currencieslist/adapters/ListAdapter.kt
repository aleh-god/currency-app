package by.godevelopment.currencyappsample.presentation.ui.currencieslist.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import by.godevelopment.currencyappsample.databinding.ItemCurrencyBinding
import by.godevelopment.currencyappsample.domain.models.ItemCurrencyModel

class ListAdapter : RecyclerView.Adapter<ListAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(val binding: ItemCurrencyBinding) : RecyclerView.ViewHolder(binding.root)

    private val diffCallback
    = object : DiffUtil.ItemCallback<ItemCurrencyModel>() {
        override fun areContentsTheSame(
            oldItem: ItemCurrencyModel,
            newItem: ItemCurrencyModel
        ): Boolean {
            return oldItem.curName == newItem.curName
        }

        override fun areItemsTheSame(
            oldItem: ItemCurrencyModel,
            newItem: ItemCurrencyModel
        ): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    var listItems: List<ItemCurrencyModel>
        get() = differ.currentList
        set(value) { differ.submitList(value) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            ItemCurrencyBinding.inflate(
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
            curScale.text = item.scale.toString()
            curName.text = item.curName
            curValueOld.text = item.curValueOld
            curValueNew.text = item.curValueNew
        }
    }

    override fun getItemCount(): Int = listItems.size
}