package com.nikitosii.studyrealtorapp.flow.dashboard.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.nikitosii.studyrealtorapp.R
import com.nikitosii.studyrealtorapp.core.source.local.model.request.SearchRequest
import com.nikitosii.studyrealtorapp.databinding.ItemPropertyRequestBinding

class DashboardRequestAdapter(
    private val onClick: (View, SearchRequest) -> Unit
) :
    ListAdapter<SearchRequest, DashboardRequestViewHolder>(SaleRequestDiffCallback) {

    private val listOfFavorites = mutableSetOf<Int?>()
    private val isFavorite: (SearchRequest) -> Boolean = { listOfFavorites.contains(it.id) }
    private val onItemClick: (View, SearchRequest) -> Unit = { view, data ->
        onClick(view, data)
        if (view.id == R.id.lavFavorite)
            if (listOfFavorites.contains(data.id)) listOfFavorites.remove(data.id)
            else listOfFavorites.add(data.id)
    }

    override fun submitList(list: List<SearchRequest>?) {
        super.submitList(list)
        listOfFavorites.addAll(list?.filter { it.favorite }?.map { it.id } ?: listOf())
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardRequestViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemPropertyRequestBinding.inflate(inflater, parent, false)
        return DashboardRequestViewHolder(binding, onItemClick, isFavorite)
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