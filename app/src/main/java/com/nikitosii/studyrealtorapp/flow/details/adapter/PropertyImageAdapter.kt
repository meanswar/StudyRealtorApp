package com.nikitosii.studyrealtorapp.flow.details.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.nikitosii.studyrealtorapp.core.source.local.model.Photo
import com.nikitosii.studyrealtorapp.databinding.ItemPhotoBinding

class PropertyImageAdapter(
    private val onClick: (Int, View) -> Unit
): ListAdapter<Photo, PropertyImageViewHolder>(PhotoDiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PropertyImageViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemPhotoBinding.inflate(inflater, parent, false)
        return PropertyImageViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(holder: PropertyImageViewHolder, position: Int) {
        holder.bind(getItem(position), position)
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