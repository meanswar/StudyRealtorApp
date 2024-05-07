package com.nikitosii.studyrealtorapp.flow.dashboard

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.nikitosii.studyrealtorapp.R
import com.nikitosii.studyrealtorapp.core.source.local.model.request.PropertyRequest
import com.nikitosii.studyrealtorapp.databinding.ItemRecentSearchBinding
import com.nikitosii.studyrealtorapp.util.ext.model.getFiltersCount
import com.nikitosii.studyrealtorapp.util.ext.showText

class SaleRequestViewHolder(
    private val binding: ItemRecentSearchBinding,
    private val onClick: (PropertyRequest) -> Unit
): RecyclerView.ViewHolder(binding.root) {

    fun bind(saleRequest: PropertyRequest) {
        with(binding) {
            root.setOnClickListener { onClick(saleRequest) }
            tvSearchPlace.text = saleRequest.address
            tvPrice.showText(saleRequest.priceMax, R.string.item_filter_price_max, false)
            tvBeds.showText(saleRequest.bedsMin, R.string.item_filter_beds_min, false)
            tvMoreFilters.showText(saleRequest.getFiltersCount(), R.string.item_filter_more_count, false)
            tvRequestType.text = saleRequest.requestType
            Glide
                .with(ivSearch)
                .load(saleRequest.imageUrl)
                .placeholder(R.drawable.london)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .skipMemoryCache(true)
                .into(ivSearch)
        }
    }
}