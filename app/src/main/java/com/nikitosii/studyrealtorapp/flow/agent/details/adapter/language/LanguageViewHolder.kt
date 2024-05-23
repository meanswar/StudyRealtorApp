package com.nikitosii.studyrealtorapp.flow.agent.details.adapter.language

import androidx.recyclerview.widget.RecyclerView
import com.nikitosii.studyrealtorapp.core.source.local.model.agent.Language
import com.nikitosii.studyrealtorapp.databinding.ItemLanguageBinding

class LanguageViewHolder(private val binding: ItemLanguageBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(data: String) {
        binding.tvLanguage.text = data
    }
}