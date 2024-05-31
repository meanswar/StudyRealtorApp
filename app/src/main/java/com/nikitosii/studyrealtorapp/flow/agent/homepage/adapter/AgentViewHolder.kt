package com.nikitosii.studyrealtorapp.flow.agent.homepage.adapter

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.nikitosii.studyrealtorapp.R
import com.nikitosii.studyrealtorapp.core.source.local.model.agent.Agent
import com.nikitosii.studyrealtorapp.databinding.ItemAgentBinding
import com.nikitosii.studyrealtorapp.util.ext.ifNullOrEmpty
import com.nikitosii.studyrealtorapp.util.ext.onClick
import com.nikitosii.studyrealtorapp.util.ext.show

class AgentViewHolder(
    private val binding: ItemAgentBinding,
    private val onClick: (View, Agent) -> Unit
): RecyclerView.ViewHolder(binding.root) {

    fun bind(agent: Agent) {
        with(binding) {
            setFavorite(agent.favorite)
            tvAgentName.text = agent.name.ifNullOrEmpty(agent.nickname).ifNullOrEmpty(agent.fullName)
            tvAgentOffice.text = agent.office?.name
            tvAgentPhone.text = agent.phone
            clAgentContent.onClick { onClick(clAgentContent, agent) }
            cvFavorite.onClick { onClick(cvFavorite, agent); onFavoriteClicked(agent) }
            cvEmail.onClick { onClick(cvEmail, agent) }
            cvPhone.onClick { onClick(cvPhone, agent) }
            setImage(agent.photoUrl, ivAgent, R.drawable.agent)
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

    private fun setImage(url: String?, view: ImageView, placeHolder: Int) {
        view.show()
        Glide.with(view)
            .load(url)
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .placeholder(placeHolder)
            .skipMemoryCache(false)
            .into(view)
    }
}