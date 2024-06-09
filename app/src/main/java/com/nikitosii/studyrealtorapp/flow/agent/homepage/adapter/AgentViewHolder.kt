package com.nikitosii.studyrealtorapp.flow.agent.homepage.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.nikitosii.studyrealtorapp.R
import com.nikitosii.studyrealtorapp.core.source.local.model.agent.Agent
import com.nikitosii.studyrealtorapp.databinding.ItemAgentBinding
import com.nikitosii.studyrealtorapp.util.ext.glideImage
import com.nikitosii.studyrealtorapp.util.ext.ifNullOrEmpty
import com.nikitosii.studyrealtorapp.util.ext.onClick
import com.nikitosii.studyrealtorapp.util.ext.onFavorite

class AgentViewHolder(
    private val binding: ItemAgentBinding,
    private val isFavorite: (Agent) -> Boolean,
    private val onClick: (View, Agent) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(agent: Agent) {
        with(binding) {
            cvAgentContent.transitionName = agent.id
            setFavorite(agent)
            onClicks(agent)
            setTexts(agent)
            glideImage(ivAgent, agent.photoUrl, R.drawable.agent)
            glideImage(ivOffice, agent.office?.image)
        }
    }

    private fun onClicks(agent: Agent) {
        with(binding) {
            cvAgentContent.onClick { onClick(cvAgentContent, agent) }
            lavFavorite.onClick { lavFavorite.playAnimation() }
            cvEmail.onClick { onClick(cvEmail, agent) }
            cvPhone.onClick { onClick(cvPhone, agent) }
        }
    }

    private fun setTexts(agent: Agent) {
        with(binding) {
            tvAgentName.text =
                agent.name.ifNullOrEmpty(agent.fullName).ifNullOrEmpty(agent.nickname)
            tvAgentOffice.text = agent.office?.name
            tvAgentPhone.text = agent.phone
        }
    }

    private fun setFavorite(data: Agent) {
        binding.lavFavorite.onFavorite(
            { isFavorite(data) },
            { onClick(binding.lavFavorite, data) })
    }
}