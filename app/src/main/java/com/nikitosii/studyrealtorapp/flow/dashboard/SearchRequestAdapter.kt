package com.nikitosii.studyrealtorapp.flow.dashboard

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.nikitosii.studyrealtorapp.core.source.local.model.request.SearchRequest
import com.nikitosii.studyrealtorapp.databinding.ItemRecentSearchBinding

class SearchRequestAdapter(
    private val onClick: (View, SearchRequest) -> Unit,
    private val isFullWidth: Boolean = false
) :
    ListAdapter<SearchRequest, RequestViewHolder>(SaleRequestDiffCallback) {

    @SuppressLint("SuspiciousIndentation")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RequestViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemRecentSearchBinding.inflate(inflater, parent, false)
        if (!isFullWidth)
        binding.root.updateLayoutParams { width = (parent.width * 0.8).toInt()}
        return RequestViewHolder(binding, onClick, isFullWidth)
    }

    override fun onBindViewHolder(holder: RequestViewHolder, position: Int) {
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