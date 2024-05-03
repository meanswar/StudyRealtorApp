package com.nikitosii.studyrealtorapp.flow.property_photos

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.nikitosii.studyrealtorapp.core.source.local.model.Photo
import com.nikitosii.studyrealtorapp.databinding.ItemPropertyPhotoBinding

class PropertyPhotoAdapter: ListAdapter<Photo, PropertyPhotosViewHolder>(PhotoDiffCallback) {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): PropertyPhotosViewHolder {
        val binding = ItemPropertyPhotoBinding.inflate(LayoutInflater.from(p0.context), p0, false)
        return PropertyPhotosViewHolder(binding)
    }

    override fun onBindViewHolder(p0: PropertyPhotosViewHolder, p1: Int) {
        p0.bind(getItem(p1))
    }

    object PhotoDiffCallback : DiffUtil.ItemCallback<Photo>() {
        override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean {
            return oldItem == newItem
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean {
            return oldItem.url == newItem.url
        }
    }
}