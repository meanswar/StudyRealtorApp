package com.nikitosii.studyrealtorapp.flow.agent.details.adapter.language

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.nikitosii.studyrealtorapp.databinding.ItemLanguageBinding

class LanguageAdapter : ListAdapter<String, LanguageViewHolder>(LanguageDiffUtil) {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): LanguageViewHolder =
        LanguageViewHolder(ItemLanguageBinding.inflate(LayoutInflater.from(p0.context), p0, false))

    override fun onBindViewHolder(holder: LanguageViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    object LanguageDiffUtil : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }
}