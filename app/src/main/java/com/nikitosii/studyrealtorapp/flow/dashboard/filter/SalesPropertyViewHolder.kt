package com.nikitosii.studyrealtorapp.flow.dashboard.filter

import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.nikitosii.studyrealtorapp.core.source.local.model.Branding
import com.nikitosii.studyrealtorapp.core.source.local.model.Property
import com.nikitosii.studyrealtorapp.databinding.ItemSalesBinding
import com.nikitosii.studyrealtorapp.util.ext.hide
import com.nikitosii.studyrealtorapp.util.ext.model.getName
import com.nikitosii.studyrealtorapp.util.ext.onClick
import com.nikitosii.studyrealtorapp.util.ext.show
import com.nikitosii.studyrealtorapp.util.ext.showText

class SalesPropertyViewHolder(
    private val binding: ItemSalesBinding,
    private val onItemClick: (Property, view: ImageView) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(property: Property) {
        with(binding) {
            setPropertyImage(property)
            setBrandingLogoData(property.branding?.first())

            tvPropertyAddress.text = property.getName()
            tvPropertyPrice.text = property.listPrice?.toString()

            tvPropertyBeds.showText(property.description?.beds?.toString())
            tvProperyBaths.showText(property.description?.baths?.toString())
            tvPropertySqft.showText(property.description?.sqft?.toString())

            root.onClick { onItemClick(property, ivProperty) }
        }
    }

    private fun setBrandingLogoData(data: Branding?) {
        with(binding) {
            val brandingLogo = data?.photo
            if (brandingLogo != null) setImage(brandingLogo, binding.ivBrandingLogo)
            else {
                ivBrandingLogo.hide()
                tvBrandingName.showText(data?.name)
            }
        }
    }

    private fun setPropertyImage(data: Property) {
        with(binding) {
//            lavSoldProperty.show(data.description?.soldDate == null || data.description.soldPrice == null)
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