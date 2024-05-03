package com.nikitosii.studyrealtorapp.flow.details.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.nikitosii.studyrealtorapp.core.source.local.model.property_details.Details
import com.nikitosii.studyrealtorapp.databinding.ItemPropertyDetailsBinding

class PropertyDetailsAdapter: ListAdapter<Details, PropertyDetailsViewHolder>(DetailsDiffCallback) {

    override fun onCreateViewHolder(view: ViewGroup, parentType: Int): PropertyDetailsViewHolder {
        val binding = ItemPropertyDetailsBinding.inflate(LayoutInflater.from(view.context))
        binding.root.layoutParams = ViewGroup.LayoutParams((view.width * 0.8).toInt(), ViewGroup.LayoutParams.MATCH_PARENT)
        return PropertyDetailsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PropertyDetailsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    object DetailsDiffCallback : DiffUtil.ItemCallback<Details>() {
        override fun areItemsTheSame(oldItem: Details, newItem: Details): Boolean {
            return oldItem == newItem
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: Details, newItem: Details): Boolean {
            return oldItem.category == newItem.category &&
                    oldItem.parentCategory == newItem.parentCategory &&
                    oldItem.text?.joinToString("\n") == newItem.text?.joinToString("\n")
        }
    }
}