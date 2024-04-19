package com.nikitosii.studyrealtorapp.flow.sales

import androidx.recyclerview.widget.RecyclerView
import com.nikitosii.studyrealtorapp.R
import com.nikitosii.studyrealtorapp.core.source.local.model.request.SalesRequest
import com.nikitosii.studyrealtorapp.databinding.ItemRecentSearchBinding
import com.nikitosii.studyrealtorapp.databinding.ItemSalesBinding
import com.nikitosii.studyrealtorapp.util.ext.model.getFiltersCount
import com.nikitosii.studyrealtorapp.util.ext.showText

class SaleRequestViewHolder(
    private val binding: ItemRecentSearchBinding,
    private val onClick: (SalesRequest) -> Unit
): RecyclerView.ViewHolder(binding.root) {

    fun bind(saleRequest: SalesRequest) {
        binding.apply {
            root.setOnClickListener { onClick(saleRequest) }
            tvSearchPlace.text = saleRequest.address
            tvPrice.showText(saleRequest.priceMax, R.string.item_filter_price_max)
            tvBeds.showText(saleRequest.bedsMin, R.string.item_filter_beds_min)
            tvMoreFilters.showText(saleRequest.getFiltersCount(), R.string.item_filter_more_count)
        }
    }

}