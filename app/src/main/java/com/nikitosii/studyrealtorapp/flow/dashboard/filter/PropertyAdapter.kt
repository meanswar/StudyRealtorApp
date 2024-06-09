package com.nikitosii.studyrealtorapp.flow.dashboard.filter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.nikitosii.studyrealtorapp.core.source.local.model.Property
import com.nikitosii.studyrealtorapp.core.source.local.model.agent.Agent
import com.nikitosii.studyrealtorapp.databinding.ItemSalesBinding

class PropertyAdapter(
    private val onItemClick: (Property) -> Unit,
    private val onFavoriteClick: (Property) -> Unit
) : ListAdapter<Property, PropertyViewHolder>(PropertyDiffUtil) {

    private val listOfFavorites = mutableListOf<String>()

    private val isFavorite: (Property) -> Boolean = { listOfFavorites.contains(it.propertyId) }
    private val onFavoriteClicked: (Property) -> Unit = { data ->
        if (listOfFavorites.contains(data.propertyId)) listOfFavorites.remove(data.propertyId)
        else listOfFavorites.add(data.propertyId)
        onFavoriteClick(data)
    }

    override fun submitList(list: List<Property>?) {
        super.submitList(list)
        listOfFavorites.addAll(list?.filter { it.favorite }?.map { it.propertyId } ?: listOf())
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PropertyViewHolder {
        val binding = ItemSalesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PropertyViewHolder(binding, onItemClick, onFavoriteClicked, isFavorite)
    }

    override fun onBindViewHolder(holder: PropertyViewHolder, position: Int) {
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