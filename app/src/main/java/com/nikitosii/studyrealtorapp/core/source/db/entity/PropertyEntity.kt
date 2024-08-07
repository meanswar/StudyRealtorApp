package com.nikitosii.studyrealtorapp.core.source.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nikitosii.studyrealtorapp.core.source.local.model.Advertiser
import com.nikitosii.studyrealtorapp.core.source.local.model.Branding
import com.nikitosii.studyrealtorapp.core.source.local.model.Description
import com.nikitosii.studyrealtorapp.core.source.local.model.Flags
import com.nikitosii.studyrealtorapp.core.source.local.model.Location
import com.nikitosii.studyrealtorapp.core.source.local.model.Photo
import com.nikitosii.studyrealtorapp.core.source.local.model.Property
import com.nikitosii.studyrealtorapp.core.source.local.model.VrTour

@Entity(tableName = "property")
data class PropertyEntity(
    val advertisers: List<Advertiser> = listOf(),
    val branding: List<Branding>,
    val comingSoonDate: String?,
    val description: Description?,
    val flags: Flags?,
    val lastUpdateDate: String?,
    val listDate: String?,
    val listPrice: Int?,
    val listPriceMax: Int = 0,
    val listPriceMin: Int = 0,
    val location: Location?,
    val photos: List<Photo>,
    val priceReducedAmount: String?,
    val primaryPhoto: Photo,
    @PrimaryKey val propertyId: String,
    val status: String?,
    val tags: List<String>,
    val virtualTours: List<VrTour>,
    val favorite: Boolean
) {
    companion object {
        fun from(data: Property): PropertyEntity = PropertyEntity(
            data.advertisers,
            data.branding,
            data.comingSoonDate,
            data.description,
            data.flags,
            data.lastUpdateDate,
            data.listDate,
            data.listPrice,
            data.listPriceMax,
            data.listPriceMin,
            data.location,
            data.photos,
            data.priceReducedAmount,
            data.primaryPhoto,
            data.propertyId,
            data.status,
            data.tags,
            data.virtualTours,
            favorite = data.favorite
        )
    }
}