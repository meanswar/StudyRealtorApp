package com.nikitosii.studyrealtorapp.flow.agent.details.adapter.serving_area

import androidx.recyclerview.widget.RecyclerView
import com.nikitosii.studyrealtorapp.core.source.local.model.agent.Area
import com.nikitosii.studyrealtorapp.databinding.ItemTextBinding

class ServedAreaViewHolder(private val binding: ItemTextBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(data: Area) {
        binding.tvTitle.text = "${data.name}, ${data.stateCode}"
    }
}