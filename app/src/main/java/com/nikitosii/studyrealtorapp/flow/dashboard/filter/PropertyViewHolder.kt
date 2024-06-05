package com.nikitosii.studyrealtorapp.flow.dashboard.filter

import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.nikitosii.studyrealtorapp.R
import com.nikitosii.studyrealtorapp.core.source.local.model.Property
import com.nikitosii.studyrealtorapp.databinding.ItemSalesBinding
import com.nikitosii.studyrealtorapp.util.ext.model.getAddress
import com.nikitosii.studyrealtorapp.util.ext.model.getPriceStringFormat
import com.nikitosii.studyrealtorapp.util.ext.onClick
import com.nikitosii.studyrealtorapp.util.ext.show
import com.nikitosii.studyrealtorapp.util.ext.showText

class PropertyViewHolder(
    private val binding: ItemSalesBinding,
    private val onItemClick: (Property) -> Unit,
    private val onFavoriteClick: (Property) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(property: Property) {
        with(binding) {
            setPropertyImage(property)
            initFavoriteView(property)

            tvPropertyAddress.text = property.getAddress()
            tvPropertyName.showText(property.description?.name)
            val price = property.getPriceStringFormat()
            tvPropertyPrice.text = price
            tvPropertyBeds.showText(property.description?.beds?.toString())
            tvProperyBaths.showText(property.description?.baths?.toString())
            tvPropertySqft.showText(property.description?.sqft?.toString())
            tvPropertyType.text = property.description?.type?.type

            cvContent.onClick { onItemClick(property) }
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
            ivFavorite.setImageResource(
                if (property.favorite) R.drawable.ic_favorite_active
                else R.drawable.ic_favorite
            )
        }
    }

    private fun setImage(url: String?, view: ImageView) {
        view.show()
        Glide.with(view)
            .load(url)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .transition(DrawableTransitionOptions.withCrossFade(200))
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