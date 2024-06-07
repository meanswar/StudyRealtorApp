package com.nikitosii.studyrealtorapp.flow.property_photos

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.nikitosii.studyrealtorapp.R
import com.nikitosii.studyrealtorapp.core.source.local.model.Photo
import com.nikitosii.studyrealtorapp.databinding.ItemPropertyPhotoBinding

class PropertyPhotosViewHolder(private val binding: ItemPropertyPhotoBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(data: Photo) {
        Glide
            .with(binding.root)
            .load(data.url)
            .placeholder(R.drawable.london)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .skipMemoryCache(false)
            .into(binding.ivImage)
    }
}