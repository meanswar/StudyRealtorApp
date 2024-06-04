package com.nikitosii.studyrealtorapp.flow.dashboard.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.nikitosii.studyrealtorapp.core.source.local.model.request.SearchRequest
import com.nikitosii.studyrealtorapp.databinding.ItemPropertyRequestBinding
import com.nikitosii.studyrealtorapp.databinding.ItemRecentSearchBinding

class DashboardRequestAdapter(
    private val onClick: (View, SearchRequest) -> Unit
) :
    ListAdapter<SearchRequest, DashboarRequestViewHolder>(SaleRequestDiffCallback) {

    @SuppressLint("SuspiciousIndentation")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboarRequestViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemPropertyRequestBinding.inflate(inflater, parent, false)
        binding.root.updateLayoutParams { width = (parent.width * 0.8).toInt()}
        return DashboarRequestViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(holder: DashboarRequestViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    object SaleRequestDiffCallback : DiffUtil.ItemCallback<SearchRequest>() {
        override fun areItemsTheSame(oldItem: SearchRequest, newItem: SearchRequest): Boolean {
            return oldItem == newItem
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: SearchRequest, newItem: SearchRequest): Boolean {
            return oldItem == newItem
        }
    }
}