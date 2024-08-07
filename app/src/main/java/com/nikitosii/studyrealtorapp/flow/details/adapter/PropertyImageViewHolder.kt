package com.nikitosii.studyrealtorapp.flow.details.adapter

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.nikitosii.studyrealtorapp.core.source.local.model.Photo
import com.nikitosii.studyrealtorapp.databinding.ItemPhotoBinding

class PropertyImageViewHolder(
    private val binding: ItemPhotoBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Photo) {
        Glide.with(binding.root)
            .load(item.url)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .skipMemoryCache(false)
            .transition(DrawableTransitionOptions.withCrossFade(200))
            .into(binding.ivPhoto)
    }
}