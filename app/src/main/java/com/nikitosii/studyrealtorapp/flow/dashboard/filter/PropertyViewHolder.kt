package com.nikitosii.studyrealtorapp.flow.dashboard.filter

import android.widget.ImageView
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.nikitosii.studyrealtorapp.R
import com.nikitosii.studyrealtorapp.core.source.local.model.Property
import com.nikitosii.studyrealtorapp.databinding.ItemSalesBinding
import com.nikitosii.studyrealtorapp.util.ext.formatPrice
import com.nikitosii.studyrealtorapp.util.ext.model.getName
import com.nikitosii.studyrealtorapp.util.ext.onClick
import com.nikitosii.studyrealtorapp.util.ext.show
import com.nikitosii.studyrealtorapp.util.ext.showText

class PropertyViewHolder(
    private val binding: ItemSalesBinding,
    private val onItemClick: (Property, view: ImageView) -> Unit,
    private val onFavoriteClick: (Property) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(property: Property) {
        with(binding) {
            ViewCompat.setTransitionName(ivProperty, property.propertyId)
            setPropertyImage(property)
            initFavoriteView(property)

            tvPropertyAddress.text = property.getName()
            tvPropertyPrice.text = property.listPrice?.toString()?.formatPrice()

            tvPropertyBeds.showText(property.description?.beds?.toString())
            tvProperyBaths.showText(property.description?.baths?.toString())
            tvPropertySqft.showText(property.description?.sqft?.toString())
            tvPropertyType.text = property.description?.type?.type

            root.onClick { onItemClick(property, ivProperty) }
            ivFavorite.onClick { onFavoriteClicked(property) }
        }
    }

    private fun setPropertyImage(data: Property) {
        with(binding) {
            setImage(data.primaryPhoto.url, ivProperty)
        }
    }

    private fun initFavoriteView(property: Property) {
        with(binding) {
            when (property.favorite) {
                true -> {
                    cvFavorite.setCardBackgroundColor(root.context.getColor(R.color.peach))
                    ivFavorite.setImageResource(R.drawable.ic_favorite_active)
                }
                false -> {
                    cvFavorite.setCardBackgroundColor(root.context.getColor(R.color.gray))
                    ivFavorite.setImageResource(R.drawable.ic_favorite)
                }
            }
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

    private fun onFavoriteClicked(property: Property) {
        onFavoriteClick(property)
        val favorite = !property.favorite
        val newProperty = property.copy(favorite = favorite)
        bind(newProperty)
    }
}