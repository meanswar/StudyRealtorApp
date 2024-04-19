package com.nikitosii.studyrealtorapp.flow.sales.filter

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.nikitosii.studyrealtorapp.core.source.local.model.HouseType
import com.nikitosii.studyrealtorapp.databinding.ItemFilterHouseBinding
import com.nikitosii.studyrealtorapp.util.ext.onClick
import com.nikitosii.studyrealtorapp.util.ext.selectedItem
import com.nikitosii.studyrealtorapp.util.ext.unselectedItem

class FilterViewHolder(
    private val binding: ItemFilterHouseBinding,
    private val onClick: (HouseType) -> Boolean
) : ViewHolder(binding.root) {

    fun bind(item: HouseType, isSelected: Boolean) {
        onSelected(isSelected)
        with(binding.tvHouse) {
            text = item.type
            onClick { onSelected(onClick(item)) }
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun onSelected(isSelected: Boolean) {
        binding.tvHouse.run { if (isSelected) selectedItem() else unselectedItem() }
    }
}