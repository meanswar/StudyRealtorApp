package com.nikitosii.studyrealtorapp.flow.details.adapter.schools

import androidx.recyclerview.widget.RecyclerView
import com.nikitosii.studyrealtorapp.core.source.local.model.property_details.School
import com.nikitosii.studyrealtorapp.databinding.ItemTextInfoBinding

class TextViewHolder(private val binding: ItemTextInfoBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(data: School) {
        binding.tvInfo.text = data.toString()
    }
}