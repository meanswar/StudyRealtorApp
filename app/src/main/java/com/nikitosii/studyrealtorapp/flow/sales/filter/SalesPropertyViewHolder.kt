package com.nikitosii.studyrealtorapp.flow.sales.filter

import android.os.Handler
import android.os.Looper
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.nikitosii.studyrealtorapp.core.source.local.model.Property
import com.nikitosii.studyrealtorapp.databinding.ItemSalesBinding
import com.nikitosii.studyrealtorapp.util.ext.model.getName
import com.nikitosii.studyrealtorapp.util.ext.onClick

class SalesPropertyViewHolder(
    private val binding: ItemSalesBinding,
    private val onItemClick: (Property) -> Unit
    ): RecyclerView.ViewHolder(binding.root) {
    fun bind(property: Property) {
        with(binding) {
            tvHouseTitle.text = property.getName()
            Glide.with(root)
                .load(property.primaryPhoto.url)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .skipMemoryCache(false)
                .into(ivSales)
            tvSalesPrice.text = property.listPrice?.toString()
            tvRooms.text = property.description?.baths?.toString()
            root.onClick { onItemClick(property) }
        }

    }
}