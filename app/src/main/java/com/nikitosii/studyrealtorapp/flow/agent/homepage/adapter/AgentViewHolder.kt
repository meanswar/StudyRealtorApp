package com.nikitosii.studyrealtorapp.flow.agent.homepage.adapter

import android.annotation.SuppressLint
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.nikitosii.studyrealtorapp.R
import com.nikitosii.studyrealtorapp.core.source.local.model.agent.Agent
import com.nikitosii.studyrealtorapp.databinding.ItemAgentBinding
import com.nikitosii.studyrealtorapp.util.ext.ifNullOrEmpty
import com.nikitosii.studyrealtorapp.util.ext.onAnimCompleted
import com.nikitosii.studyrealtorapp.util.ext.onClick
import com.nikitosii.studyrealtorapp.util.ext.show

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
            setImage(agent.photoUrl, ivAgent, R.drawable.agent)
            setImage(agent.office?.image, ivOffice)
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

    private fun setFavorite(agent: Agent) {
        with(binding.lavFavorite) {
            if (isFavorite(agent)) {
                frame = maxFrame.toInt()
                speed = -6.0f
            } else {
                frame = minFrame.toInt()
                speed = 6.0f
            }
            onAnimCompleted(onCompleted = {
                speed *= -1
                onClick(this, agent)
            })
        }
    }

        @SuppressLint("CheckResult")
        private fun setImage(url: String?, view: ImageView, placeHolder: Int? = null) {
            view.show()
            Glide.with(view)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transition(DrawableTransitionOptions.withCrossFade(200))
                .apply { if (placeHolder != null) placeholder(placeHolder) }
                .skipMemoryCache(false)
                .into(view)
        }
    }