package com.nikitosii.studyrealtorapp.flow.dashboard.filter

import android.widget.ImageView
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.nikitosii.studyrealtorapp.core.source.local.model.Property
import com.nikitosii.studyrealtorapp.databinding.ItemSalesBinding
import com.nikitosii.studyrealtorapp.util.ext.formatPrice
import com.nikitosii.studyrealtorapp.util.ext.model.getName
import com.nikitosii.studyrealtorapp.util.ext.onClick
import com.nikitosii.studyrealtorapp.util.ext.show
import com.nikitosii.studyrealtorapp.util.ext.showText

class PropertyViewHolder(
    private val binding: ItemSalesBinding,
    private val onItemClick: (Property, view: ImageView) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(property: Property) {
        with(binding) {
            ViewCompat.setTransitionName(ivProperty, property.propertyId)
            setPropertyImage(property)

            tvPropertyAddress.text = property.getName()
            tvPropertyPrice.text = property.listPrice?.toString()?.formatPrice()

            tvPropertyBeds.showText(property.description?.beds?.toString())
            tvProperyBaths.showText(property.description?.baths?.toString())
            tvPropertySqft.showText(property.description?.sqft?.toString())
            tvPropertyType.showText(property.description?.type?.type)

            root.onClick { onItemClick(property, ivProperty) }
        }
    }

    private fun setPropertyImage(data: Property) {
        with(binding) {
            setImage(data.primaryPhoto.url, ivProperty)
        }
    }

    private fun setImage(url: String?, view: ImageView) {
        view.show()
        Glide.with(view)
            .load(url)
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .skipMemoryCache(false)
            .into(view)
    }
}