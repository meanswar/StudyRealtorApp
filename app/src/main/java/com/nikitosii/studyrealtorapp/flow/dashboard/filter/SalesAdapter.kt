package com.nikitosii.studyrealtorapp.flow.dashboard.filter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.nikitosii.studyrealtorapp.core.source.local.model.Property
import com.nikitosii.studyrealtorapp.databinding.ItemSalesBinding

class SalesAdapter(private val onItemClick: (Property, View) -> Unit) :
    ListAdapter<Property, SalesPropertyViewHolder>(PropertyDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SalesPropertyViewHolder {
        val binding = ItemSalesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SalesPropertyViewHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(holder: SalesPropertyViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    object PropertyDiffUtil : DiffUtil.ItemCallback<Property>() {
        override fun areItemsTheSame(oldItem: Property, newItem: Property): Boolean {
            return oldItem == newItem
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: Property, newItem: Property): Boolean {
            return oldItem.propertyId == newItem.propertyId
        }
    }
}