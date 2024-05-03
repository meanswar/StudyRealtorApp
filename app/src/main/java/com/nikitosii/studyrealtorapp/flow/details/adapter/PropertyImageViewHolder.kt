package com.nikitosii.studyrealtorapp.flow.details.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.nikitosii.studyrealtorapp.core.source.local.model.Photo
import com.nikitosii.studyrealtorapp.databinding.ItemPhotoBinding
import com.nikitosii.studyrealtorapp.util.ext.onClick

class PropertyImageViewHolder(
    private val binding: ItemPhotoBinding,
    private val onClick: (Int, View) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Photo, id: Int) {
        Glide.with(binding.root)
            .load(item.url)
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .skipMemoryCache(false)
            .into(binding.ivPhoto)
        binding.root.onClick { onClick(id, binding.ivPhoto) }
        binding.ivPhoto.transitionName = item.url
    }
}