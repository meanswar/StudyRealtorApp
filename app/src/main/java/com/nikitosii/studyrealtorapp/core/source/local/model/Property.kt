package com.nikitosii.studyrealtorapp.core.source.local.model

import android.os.Parcelable
import com.nikitosii.studyrealtorapp.core.source.db.entity.PropertyEntity
import com.nikitosii.studyrealtorapp.core.source.net.model.property.PropertyResponseApi
import kotlinx.parcelize.Parcelize

@Parcelize
data class Property(
    val branding: List<Branding>? = null,
    val comingSoonDate: String? = null,
    val description: Description? = null,
    val flags: Flags? = null,
    val lastUpdateDate: String? = null,
    val listDate: String? = null,
    val listPrice: Int? = null,
    val location: Location? = null,
    val photos: List<Photo>? = null,
    val priceReducedAmount: String? = null,
    val primaryPhoto: Photo,
    val propertyId: String,
    val status: String? = null,
    val tags: List<String>? = null,
    val virtualTours: List<VrTour>? = null,
    val favorite: Boolean = false
): Parcelable {
    companion object {
        fun from(data: PropertyResponseApi): Property = Property(
            data.branding?.map { Branding.from(it) },
            data.comingSoonDate,
            Description.from(data.description),
            Flags.from(data.flags),
            data.lastUpdateDate,
            data.listDate,
            data.listPrice,
            Location.from(data.location),
            data.photoResponseApis?.map { Photo.from(it) },
            data.priceReducedAmount,
            Photo.from(data.primaryPhotoResponseApi),
            data.propertyId,
            data.status,
            data.tags,
            data.virtualTours?.map { VrTour.from(it) }
        )

        fun from(entity: PropertyEntity): Property = Property(
            entity.branding,
            entity.comingSoonDate,
            entity.description,
            entity.flags,
            entity.lastUpdateDate,
            entity.listDate,
            entity.listPrice,
            entity.location,
            entity.photos,
            entity.priceReducedAmount,
            entity.primaryPhoto,
            entity.propertyId,
            entity.status,
            entity.tags,
            entity.virtualTours
        )
    }
}