package com.nikitosii.studyrealtorapp.flow.sales.filter

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.nikitosii.studyrealtorapp.R
import com.nikitosii.studyrealtorapp.databinding.ItemFilterHouseBinding
import com.nikitosii.studyrealtorapp.util.ext.onClick

class FilterViewHolder(
    private val binding: ItemFilterHouseBinding,
    private val onClick: (String) -> Boolean
) : ViewHolder(binding.root) {

    fun bind(item: String, isSelected: Boolean) {
        onSelected(isSelected)
        binding.tvHouse.text = item
        binding.tvHouse.onClick { onSelected(onClick(item)) }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun onSelected(isSelected: Boolean) {
        binding.tvHouse.background = binding.clItemContainer.context.getDrawable(
            if (isSelected) R.drawable.bg_selected else R.drawable.bg_not_selected
        )
    }
}