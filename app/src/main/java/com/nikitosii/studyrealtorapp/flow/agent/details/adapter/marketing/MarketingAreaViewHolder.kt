package com.nikitosii.studyrealtorapp.flow.agent.details.adapter.marketing

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.nikitosii.studyrealtorapp.core.source.local.model.agent.Area
import com.nikitosii.studyrealtorapp.databinding.ItemMarketingAreaBinding

class MarketingAreaViewHolder(private val binding: ItemMarketingAreaBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(data: Area) {
        with(binding) {
            Glide
                .with(ivImage)
                .load(data.imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transition(DrawableTransitionOptions.withCrossFade(200))
                .skipMemoryCache(false)
                .into(ivImage)
            tvZoneName.text = "${data.name}, ${data.stateCode}"
        }
    }
}