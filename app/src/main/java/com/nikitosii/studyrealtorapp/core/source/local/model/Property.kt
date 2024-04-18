package com.nikitosii.studyrealtorapp.core.source.local.model

import com.nikitosii.studyrealtorapp.core.source.db.entity.PropertyEntity
import com.nikitosii.studyrealtorapp.core.source.net.model.property.PropertyResponseApi

data class Property(
    val branding: List<Branding>? = null,
    val comingSoonDate: String? = null,
    val community: Community? = null,
    val description: Description? = null,
    val flags: Flags? = null,
    val lastUpdateDate: String? = null,
    val leadAttributes: LeadAttributes? = null,
    val listDate: String? = null,
    val listPrice: Int? = null,
    val listingId: String? = null,
    val location: Location? = null,
    val matterPort: Boolean? = null,
    val openHouses: List<HouseDescription>? = null,
    val rdc: List<Other>? = null,
    val permalink: String? = null,
    val photos: List<Photo>? = null,
    val priceReducedAmount: String? = null,
    val primaryPhoto: Photo,
    val products: Product? = null,
    val propertyId: String? = null,
    val source: Source? = null,
    val status: String? = null,
    val tags: List<String>? = null,
    val taxRecord: TaxRecord? = null,
    val virtualTours: List<Any>? = null
) {
    companion object {
        fun from(data: PropertyResponseApi): Property = Property(
            data.branding?.map { Branding.from(it) },
            data.comingSoonDate,
            Community.from(data.community),
            Description.from(data.description),
            Flags.from(data.flags),
            data.lastUpdateDate,
            LeadAttributes.from(data.leadAttributes),
            data.listDate,
            data.listPrice,
            data.listingId,
            Location.from(data.location),
            data.matterport,
            data.openHouses?.map { HouseDescription.from(it) },
            data.rdc?.map { Other.from(it) },
            data.permalink,
            data.photoResponseApis?.map { Photo.from(it) },
            data.priceReducedAmount,
            Photo.from(data.primaryPhotoResponseApi),
            Product.from(data.products),
            data.propertyId,
            Source.from(data.source),
            data.status,
            data.tags,
            TaxRecord.from(data.taxRecord),
            data.virtualTours
        )

        fun from(entity: PropertyEntity): Property = Property(
            entity.branding,
            entity.comingSoonDate,
            entity.community,
            entity.description,
            entity.flags,
            entity.lastUpdateDate,
            entity.leadAttributes,
            entity.listDate,
            entity.listPrice,
            entity.listingId,
            entity.location,
            entity.matterPort,
            entity.openHouses,
            entity.rdc,
            entity.permalink,
            entity.photos,
            entity.priceReducedAmount,
            entity.primaryPhoto,
            entity.products,
            entity.propertyId,
            entity.source,
            entity.status,
            entity.tags,
            entity.taxRecord,
            entity.virtualTours
        )
    }
}