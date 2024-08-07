package com.nikitosii.studyrealtorapp.flow.dashboard.adapter

import android.annotation.SuppressLint
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.nikitosii.studyrealtorapp.R
import com.nikitosii.studyrealtorapp.core.source.local.model.Property
import com.nikitosii.studyrealtorapp.core.source.local.model.request.SearchRequest
import com.nikitosii.studyrealtorapp.databinding.ItemPropertyRequestBinding
import com.nikitosii.studyrealtorapp.util.ext.glideImage
import com.nikitosii.studyrealtorapp.util.ext.model.getFiltersCount
import com.nikitosii.studyrealtorapp.util.ext.onClick
import com.nikitosii.studyrealtorapp.util.ext.onFavorite
import com.nikitosii.studyrealtorapp.util.ext.showText

class DashboardRequestViewHolder(
    private val binding: ItemPropertyRequestBinding,
    private val onClick: (View, SearchRequest) -> Unit,
    private val isFavorite: (SearchRequest) -> Boolean,
) : RecyclerView.ViewHolder(binding.root) {

    @SuppressLint("ClickableViewAccessibility")
    fun bind(data: SearchRequest) {
        with(binding) {
            cvContent.transitionName = data.id.toString()
            lavFavorite.onClick { lavFavorite.playAnimation() }
            cvContent.onClick { onClick(cvContent, data) }

            setFilters(data)
            setFavorite(data)

            tvSearchPlace.text = data.address
            tvRequestType.text = data.requestType.name
            glideImage(ivSearch, data.imageUrl)
        }
    }

    private fun setFavorite(data: SearchRequest) {
        binding.lavFavorite.onFavorite(
            { isFavorite(data) },
            { onClick(binding.lavFavorite, data) })
    }

    private fun setFilters(data: SearchRequest) {
        with(binding) {
            tvPrice.showText(data.priceMax, R.string.item_filter_price_max, false)
            tvBeds.showText(data.bedsMin, R.string.item_filter_beds_min, true)
            tvMoreFilters.showText(
                data.getFiltersCount(),
                R.string.item_filter_more_count,
                true
            )
        }
    }
}