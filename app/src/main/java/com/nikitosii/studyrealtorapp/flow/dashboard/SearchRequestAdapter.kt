package com.nikitosii.studyrealtorapp.flow.dashboard

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.nikitosii.studyrealtorapp.R
import com.nikitosii.studyrealtorapp.core.source.local.model.request.SearchRequest
import com.nikitosii.studyrealtorapp.databinding.ItemRecentSearchBinding

class SearchRequestAdapter(
    private val onClick: (View, SearchRequest) -> Unit
) :
    ListAdapter<SearchRequest, RequestViewHolder>(SaleRequestDiffCallback) {

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
        val favorites = list?.filter { it.favorite }?.map { it.id } ?: listOf()
        listOfFavorites.addAll(favorites)
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RequestViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemRecentSearchBinding.inflate(inflater, parent, false)
        return RequestViewHolder(binding, onItemClick, isFavorite)
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
            return oldItem.favorite == newItem.favorite &&
                    oldItem.requestType == newItem.requestType &&
                    oldItem.priceMax == newItem.priceMax &&
                    oldItem.bedsMin == newItem.bedsMin &&
                    oldItem.id == newItem.id &&
                    oldItem.bathsMax == newItem.bathsMax &&
                    oldItem.bedsMax == newItem.bedsMax
        }
    }
}