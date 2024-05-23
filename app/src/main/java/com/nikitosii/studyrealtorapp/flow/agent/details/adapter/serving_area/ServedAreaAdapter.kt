package com.nikitosii.studyrealtorapp.flow.agent.details.adapter.serving_area

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.nikitosii.studyrealtorapp.core.source.local.model.agent.Area
import com.nikitosii.studyrealtorapp.databinding.ItemTextBinding

class ServedAreaAdapter :
    ListAdapter<Area, ServedAreaViewHolder>(MarketingAreaDiffUtil) {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int) =
        ServedAreaViewHolder(
            ItemTextBinding.inflate(
                LayoutInflater.from(p0.context),
                p0,
                false
            )
        )

    override fun onBindViewHolder(holder: ServedAreaViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    object MarketingAreaDiffUtil : DiffUtil.ItemCallback<Area>() {
        override fun areItemsTheSame(oldItem: Area, newItem: Area): Boolean {
            return oldItem == newItem
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: Area, newItem: Area): Boolean {
            return oldItem.name == newItem.name && oldItem.stateCode == newItem.stateCode
        }
    }
}