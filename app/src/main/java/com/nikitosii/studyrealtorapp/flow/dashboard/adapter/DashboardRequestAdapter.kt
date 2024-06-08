package com.nikitosii.studyrealtorapp.flow.dashboard.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.nikitosii.studyrealtorapp.core.source.local.model.request.SearchRequest
import com.nikitosii.studyrealtorapp.databinding.ItemPropertyRequestBinding

class DashboardRequestAdapter(
    private val onClick: (View, SearchRequest) -> Unit
) :
    ListAdapter<SearchRequest, DashboardRequestViewHolder>(SaleRequestDiffCallback) {

    @SuppressLint("SuspiciousIndentation")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardRequestViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemPropertyRequestBinding.inflate(inflater, parent, false)
        return DashboardRequestViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(holder: DashboardRequestViewHolder, position: Int) {
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