package com.nikitosii.studyrealtorapp.flow.dashboard

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.nikitosii.studyrealtorapp.core.source.local.model.request.PropertyRequest
import com.nikitosii.studyrealtorapp.databinding.ItemRecentSearchBinding

class SaleRequestAdapter(private val onClick: (PropertyRequest) -> Unit) :
    ListAdapter<PropertyRequest, SaleRequestViewHolder>(SaleRequestDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SaleRequestViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemRecentSearchBinding.inflate(inflater, parent, false)
        return SaleRequestViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(holder: SaleRequestViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    object SaleRequestDiffCallback : DiffUtil.ItemCallback<PropertyRequest>() {
        override fun areItemsTheSame(oldItem: PropertyRequest, newItem: PropertyRequest): Boolean {
            return oldItem == newItem
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: PropertyRequest, newItem: PropertyRequest): Boolean {
            return oldItem == newItem
        }
    }
}