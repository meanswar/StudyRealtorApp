package com.nikitosii.studyrealtorapp.flow.details.adapter.schools

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.nikitosii.studyrealtorapp.core.source.local.model.property_details.School
import com.nikitosii.studyrealtorapp.databinding.ItemTextInfoBinding

class SchoolInfoAdapter : ListAdapter<School, TextViewHolder>(SchoolDiffCallback) {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): TextViewHolder {
        val binding = ItemTextInfoBinding.inflate(LayoutInflater.from(p0.context))
        binding.root.layoutParams =
            ViewGroup.LayoutParams((p0.width * 0.8).toInt(), ViewGroup.LayoutParams.MATCH_PARENT)

        return TextViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TextViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    object SchoolDiffCallback : DiffUtil.ItemCallback<School>() {
        override fun areItemsTheSame(oldItem: School, newItem: School): Boolean {
            return oldItem == newItem
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: School, newItem: School): Boolean {
            return oldItem.id == newItem.id &&
                    oldItem.name == newItem.name &&
                    oldItem.fundingType == newItem.fundingType &&
                    oldItem.educationLevels == newItem.educationLevels &&
                    oldItem.grades == newItem.grades &&
                    oldItem.rating == newItem.rating &&
                    oldItem.reviewCount == newItem.reviewCount &&
                    oldItem.distanceInMiles == newItem.distanceInMiles
        }
    }
}