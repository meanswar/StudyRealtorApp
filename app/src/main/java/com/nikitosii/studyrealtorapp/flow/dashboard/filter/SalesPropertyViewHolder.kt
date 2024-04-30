package com.nikitosii.studyrealtorapp.flow.dashboard.filter

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.nikitosii.studyrealtorapp.core.source.local.model.Property
import com.nikitosii.studyrealtorapp.databinding.ItemSalesBinding
import com.nikitosii.studyrealtorapp.util.ext.hide
import com.nikitosii.studyrealtorapp.util.ext.invisible
import com.nikitosii.studyrealtorapp.util.ext.model.getName
import com.nikitosii.studyrealtorapp.util.ext.onClick
import com.nikitosii.studyrealtorapp.util.ext.show
import com.nikitosii.studyrealtorapp.util.ext.showText

class SalesPropertyViewHolder(
    private val binding: ItemSalesBinding,
    private val onItemClick: (Property, view: View) -> Unit
    ): RecyclerView.ViewHolder(binding.root) {
    fun bind(property: Property) {
        with(binding) {
            if (property.description?.soldDate == null || property.description.soldPrice == null) {
                lavSoldStatus.show()
                ivSoldProperty.show()
                ivNotSoldProperty.invisible()
                setImage(property.primaryPhoto.url, ivSoldProperty)

            }
            else {
                lavSoldStatus.hide()
                ivSoldProperty.invisible()
                setImage(property.primaryPhoto.url, ivNotSoldProperty)
            }
            tvBrandingName.showText(property.branding?.first()?.name)
            tvHouseTitle.text = property.getName()
            tvSalesPrice.text = property.listPrice?.toString()
            tvRooms.text = property.description?.baths?.toString()
            root.onClick { onItemClick(property, ivSoldProperty) }

            if ((property.photos?.size ?: 0) >= 3) {
                setImage(property.photos?.get(0)?.url!!, ivImageStart)
                setImage(property.photos[1].url, ivImageMiddle)
                setImage(property.photos[2].url, ivImageEnd)
            }
            else {
                ivImageStart.hide()
                ivImageMiddle.hide()
                ivImageEnd.hide()
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
}