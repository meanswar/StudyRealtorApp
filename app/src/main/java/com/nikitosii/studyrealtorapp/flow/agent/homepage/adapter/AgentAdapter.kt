package com.nikitosii.studyrealtorapp.flow.agent.homepage.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.nikitosii.studyrealtorapp.R
import com.nikitosii.studyrealtorapp.core.source.local.model.agent.Agent
import com.nikitosii.studyrealtorapp.databinding.ItemAgentBinding

class AgentAdapter(
    private val onItemClick: (View, Agent) -> Unit
) : ListAdapter<Agent, AgentViewHolder>(AgentDiffCallback) {

    private val listOfFavorites = mutableListOf<String>()

    override fun submitList(list: List<Agent>?) {
        super.submitList(list)
        listOfFavorites.addAll(list?.filter { it.favorite }?.map { it.id } ?: listOf())
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): AgentViewHolder {
        val binding = ItemAgentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AgentViewHolder(binding, isFavorite, onFavoriteClicked)
    }

    private val onFavoriteClicked:(View, Agent) -> Unit = { view, data ->
        when (view.id) {
            R.id.lavFavorite ->
                if (listOfFavorites.contains(data.id)) listOfFavorites.remove(data.id)
                else listOfFavorites.add(data.id)
        }
        onItemClick(view, data)
    }

    override fun onBindViewHolder(holder: AgentViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    private val isFavorite: (Agent) -> Boolean = { listOfFavorites.contains(it.id) }
    object AgentDiffCallback : DiffUtil.ItemCallback<Agent>() {
        override fun areItemsTheSame(oldItem: Agent, newItem: Agent): Boolean {
            return oldItem == newItem
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: Agent, newItem: Agent): Boolean {
            return oldItem.id == newItem.id &&
                    oldItem.favorite == newItem.favorite &&
                    oldItem.name == newItem.name &&
                    oldItem.nickname == newItem.nickname &&
                    oldItem.fullName == newItem.fullName &&
                    oldItem.office?.name == newItem.office?.name &&
                    oldItem.office?.image == newItem.office?.image &&
                    oldItem.phone == newItem.phone &&
                    oldItem.reviewCount == newItem.reviewCount &&
                    oldItem.salePrice?.min == newItem.salePrice?.min &&
                    oldItem.salePrice?.max == newItem.salePrice?.max
        }
    }
}