package com.nikitosii.studyrealtorapp.flow.agent.homepage.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.nikitosii.studyrealtorapp.core.source.local.model.agent.Agent
import com.nikitosii.studyrealtorapp.databinding.ItemAgentBinding

class AgentAdapter(
    private val onItemClick: (View, Agent) -> Unit
) : ListAdapter<Agent, AgentViewHolder>(AgentDiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): AgentViewHolder {
        val binding = ItemAgentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AgentViewHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(holder: AgentViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    object AgentDiffCallback : DiffUtil.ItemCallback<Agent>() {
        override fun areItemsTheSame(oldItem: Agent, newItem: Agent): Boolean {
            return oldItem == newItem
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: Agent, newItem: Agent): Boolean {
            return oldItem.id == newItem.id
        }
    }
}