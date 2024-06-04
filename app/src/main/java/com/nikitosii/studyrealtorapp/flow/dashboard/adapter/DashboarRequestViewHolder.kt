package com.nikitosii.studyrealtorapp.flow.dashboard.adapter

import android.annotation.SuppressLint
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.nikitosii.studyrealtorapp.R
import com.nikitosii.studyrealtorapp.core.source.local.model.request.SearchRequest
import com.nikitosii.studyrealtorapp.databinding.ItemPropertyRequestBinding
import com.nikitosii.studyrealtorapp.databinding.ItemRecentSearchBinding
import com.nikitosii.studyrealtorapp.util.ext.model.getFiltersCount
import com.nikitosii.studyrealtorapp.util.ext.onAnimationRunning
import com.nikitosii.studyrealtorapp.util.ext.onClick
import com.nikitosii.studyrealtorapp.util.ext.showText

class DashboarRequestViewHolder(
    private val binding: ItemPropertyRequestBinding,
    private val onClick: (View, SearchRequest) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    @SuppressLint("ClickableViewAccessibility")
    fun bind(data: SearchRequest) {
        with(binding) {
            cvContent.transitionName = data.id.toString()
            cvFavorite.onClick { onFavoriteClicked(data) }
            cvContent.onClick { onClick(cvContent, data) }

            setFilters(data)
            setFavorite(data.favorite)

            tvSearchPlace.text = data.address
            tvRequestType.text = data.requestType.name
            Glide
                .with(ivSearch)
                .load(data.imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .skipMemoryCache(true)
                .into(ivSearch)
        }
    }

    private fun setFavorite(isFavorite: Boolean) {
        binding.ivFavorite.setImageResource(
            if (isFavorite) R.drawable.ic_favorite_active
            else R.drawable.ic_favorite
        )
    }

    private fun setFilters(data: SearchRequest) {
        with(binding) {
            tvPrice.showText(data.priceMax, R.string.item_filter_price_max, false)
            tvBeds.showText(data.bedsMin, R.string.item_filter_beds_min, false)
            tvMoreFilters.showText(
                data.getFiltersCount(),
                R.string.item_filter_more_count,
                false
            )
        }
    }

    private fun onFavoriteClicked(saleRequest: SearchRequest) {
        onClick(binding.cvFavorite, saleRequest)
    }
}