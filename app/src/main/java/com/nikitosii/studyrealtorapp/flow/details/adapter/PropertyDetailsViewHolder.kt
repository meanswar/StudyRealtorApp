package com.nikitosii.studyrealtorapp.flow.details.adapter

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import com.nikitosii.studyrealtorapp.R
import com.nikitosii.studyrealtorapp.core.source.local.model.property_details.Details
import com.nikitosii.studyrealtorapp.databinding.ItemPropertyDetailsBinding
import com.nikitosii.studyrealtorapp.util.ext.showText

class PropertyDetailsViewHolder(private val binding: ItemPropertyDetailsBinding) :
    RecyclerView.ViewHolder(binding.root) {

    @SuppressLint("UseCompatLoadingForDrawables")
    fun bind(data: Details) {
        with(binding) {
            tvTitle.showText(data.category)
            tvParentTitle.showText(data.parentCategory)
            tvDescription.showText(data.text?.joinToString("\n"))
        }
    }
}