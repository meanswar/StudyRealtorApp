package com.nikitosii.studyrealtorapp.core.source.local.model

import android.os.Parcelable
import com.nikitosii.studyrealtorapp.core.source.db.entity.PropertyEntity
import com.nikitosii.studyrealtorapp.core.source.net.model.property.PropertyResponseApi
import kotlinx.parcelize.Parcelize

@Parcelize
data class Property(
    val advertisers: List<Advertiser>,
    val branding: List<Branding>,
    val comingSoonDate: String? = null,
    val description: Description? = null,
    val flags: Flags? = null,
    val lastUpdateDate: String? = null,
    val listDate: String? = null,
    val listPrice: Int? = null,
    val listPriceMax: Int,
    val listPriceMin: Int,
    val location: Location? = null,
    val photos: List<Photo>,
    val priceReducedAmount: String? = null,
    val primaryPhoto: Photo,
    val propertyId: String,
    val status: String? = null,
    val tags: List<String>,
    val virtualTours: List<VrTour>,
    val favorite: Boolean = false
): Parcelable {
    companion object {
        fun from(data: PropertyResponseApi): Property = Property(
            data.advertisers?.map { Advertiser.from(it) } ?: listOf(),
            data.branding?.map { Branding.from(it) } ?: listOf(),
            data.comingSoonDate,
            Description.from(data.description),
            Flags.from(data.flags),
            data.lastUpdateDate,
            data.listDate,
            data.listPrice,
            data.listPriceMax ?: 0,
            data.listPriceMin ?: 0,
            Location.from(data.location),
            data.photos?.map { Photo.from(it) } ?: listOf(),
            data.priceReducedAmount,
            Photo.from(data.primaryPhoto),
            data.propertyId,
            data.status,
            data.tags ?: listOf(),
            data.virtualTours?.map { VrTour.from(it) } ?: listOf()
        )

        fun from(entity: PropertyEntity): Property = Property(
            entity.advertisers,
            entity.branding,
            entity.comingSoonDate,
            entity.description,
            entity.flags,
            entity.lastUpdateDate,
            entity.listDate,
            entity.listPrice,
            entity.listPriceMax,
            entity.listPriceMin,
            entity.location,
            entity.photos,
            entity.priceReducedAmount,
            entity.primaryPhoto,
            entity.propertyId,
            entity.status,
            entity.tags,
            entity.virtualTours,
            entity.favorite
        )
    }
}