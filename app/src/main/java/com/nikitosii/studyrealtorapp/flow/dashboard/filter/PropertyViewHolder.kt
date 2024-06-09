package com.nikitosii.studyrealtorapp.flow.dashboard.filter

import androidx.recyclerview.widget.RecyclerView
import com.nikitosii.studyrealtorapp.core.source.local.model.Property
import com.nikitosii.studyrealtorapp.databinding.ItemSalesBinding
import com.nikitosii.studyrealtorapp.util.ext.glideImage
import com.nikitosii.studyrealtorapp.util.ext.model.getAddress
import com.nikitosii.studyrealtorapp.util.ext.model.getPriceStringFormat
import com.nikitosii.studyrealtorapp.util.ext.onClick
import com.nikitosii.studyrealtorapp.util.ext.onFavorite
import com.nikitosii.studyrealtorapp.util.ext.showText

class PropertyViewHolder(
    private val binding: ItemSalesBinding,
    private val onItemClick: (Property) -> Unit,
    private val onFavoriteClick: (Property) -> Unit,
    private val isFavorite: (Property) -> Boolean
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(property: Property) {
        with(binding) {
            glideImage(ivProperty, property.primaryPhoto.url)
            setFavorite(property)

            tvPropertyAddress.text = property.getAddress()
            tvPropertyName.showText(property.description?.name)
            val price = property.getPriceStringFormat()
            tvPropertyPrice.text = price
            tvPropertyBeds.showText(property.description?.beds?.toString())
            tvProperyBaths.showText(property.description?.baths?.toString())
            tvPropertySqft.showText(property.description?.sqft?.toString())
            tvPropertyType.text = property.description?.type?.type

            cvContent.onClick { onItemClick(property) }
            lavFavorite.onClick { lavFavorite.playAnimation() }
        }
    }

    private fun setFavorite(data: Property) {
        binding.lavFavorite.onFavorite(
            { isFavorite(data) },
            { onFavoriteClick(data) })
    }
}