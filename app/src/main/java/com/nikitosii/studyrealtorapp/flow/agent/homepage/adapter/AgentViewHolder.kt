package com.nikitosii.studyrealtorapp.flow.agent.homepage.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.nikitosii.studyrealtorapp.R
import com.nikitosii.studyrealtorapp.core.source.local.model.agent.Agent
import com.nikitosii.studyrealtorapp.databinding.ItemAgentBinding
import com.nikitosii.studyrealtorapp.util.ext.ifNullOrEmpty
import com.nikitosii.studyrealtorapp.util.ext.onClick

class AgentViewHolder(
    private val binding: ItemAgentBinding,
    private val onClick: (View, Agent) -> Unit
): RecyclerView.ViewHolder(binding.root) {

    fun bind(agent: Agent) {
        with(binding) {
            setFavorite(agent.favorite)
            tvAgentName.text = agent.name.ifNullOrEmpty(agent.nickname).ifNullOrEmpty(agent.fullName)
            clAgentContent.onClick { onClick(clAgentContent, agent) }
            cvFavorite.onClick { onClick(cvFavorite, agent); onFavoriteClicked(agent) }
            cvEmail.onClick { onClick(cvEmail, agent) }
            cvPhone.onClick { onClick(cvPhone, agent) }
        }
    }

    private fun onFavoriteClicked(agent: Agent) {
        val favorite = !agent.favorite
        val updatedAgent = agent.copy(favorite = favorite)
        bind(updatedAgent)
    }

    private fun setFavorite(isFavorite: Boolean) {
        binding.ivFavorite.setImageResource(
            if (isFavorite) R.drawable.ic_favorite_active
            else R.drawable.ic_favorite
        )
    }
}