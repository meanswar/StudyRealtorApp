package com.nikitosii.studyrealtorapp.flow.dashboard.filter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.nikitosii.studyrealtorapp.core.source.local.model.HouseType
import com.nikitosii.studyrealtorapp.databinding.ItemFilterHouseBinding


class FilterAdapter(
    selectedList: List<HouseType> = listOf(),
    private val onClick: (HouseType) -> Boolean
): ListAdapter<HouseType, FilterViewHolder>(FilterDiffCallback) {

   private val selectedList = selectedList.toMutableList()

    fun setSelectedList(list: List<HouseType>) {
        selectedList.clear()
        selectedList.addAll(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemFilterHouseBinding.inflate(inflater, parent, false)
        return FilterViewHolder(binding) { setFilterHouse(it); onClick(it) }
    }

    override fun onBindViewHolder(holder: FilterViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, selectedList.contains(item))
        setScaleAnimation(holder.itemView)
    }

    private fun setFilterHouse(filter: HouseType) {
        if (selectedList.contains(filter)) selectedList.remove(filter)
         else selectedList.add(filter)

    }

    private fun setScaleAnimation(view: View) {
        val anim = ScaleAnimation(
            0.0f,
            1.0f,
            0.0f,
            1.0f,
            Animation.RELATIVE_TO_SELF,
            0.5f,
            Animation.RELATIVE_TO_SELF,
            0.5f
        )
        anim.setDuration(FADE_DURATION)
        view.startAnimation(anim)
    }

    object FilterDiffCallback : DiffUtil.ItemCallback<HouseType>() {
        override fun areItemsTheSame(oldItem: HouseType, newItem: HouseType): Boolean {
            return oldItem == newItem
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: HouseType, newItem: HouseType): Boolean {
            return oldItem.type == newItem.type && oldItem.apiType == newItem.apiType
        }
    }

    companion object {
        private const val FADE_DURATION = 500L
    }
}