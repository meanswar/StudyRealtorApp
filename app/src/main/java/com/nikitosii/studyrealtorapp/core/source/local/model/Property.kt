package com.nikitosii.studyrealtorapp.core.source.local.model

import com.nikitosii.studyrealtorapp.core.source.local.model.Source
import com.nikitosii.studyrealtorapp.core.source.local.model.TaxRecord
import com.nikitosii.studyrealtorapp.core.source.net.model.data.DataResponseApi
import com.nikitosii.studyrealtorapp.core.source.net.model.property.PropertyResponseApi

data class Property(
    val branding: List<DataResponseApi>? = null,
    val comingSoonDate: String? = null,
    val community: String? = null,
    val description: Description? = null,
    val flags: Flags? = null,
    val lastUpdateDate: String? = null,
    val leadAttributes: LeadAttributes? = null,
    val listDate: String? = null,
    val listPrice: Int? = null,
    val listingId: String? = null,
    val location: Location? = null,
    val matterPort: Boolean? = null,
    val openHouses: Boolean? = null,
    val rdc: List<Other>? = null,
    val permalink: String? = null,
    val photoResponseApis: List<Photo>? = null,
    val priceReducedAmount: String? = null,
    val primaryPhotoResponseApi: Photo,
    val products: Product? = null,
    val propertyId: String? = null,
    val source: Source? = null,
    val status: String? = null,
    val tags: List<String>? = null,
    val taxRecord: TaxRecord? = null,
    val virtualTours: List<Object>? = null
) {
    companion object {
        fun from(data: PropertyResponseApi): Property = Property(
            data.branding,
            data.comingSoonDate,
            data.community,
            Description.from(data.description),
            Flags.from(data.flags),
            data.lastUpdateDate,
            LeadAttributes.from(data.leadAttributes),
            data.listDate,
            data.listPrice,
            data.listingId,
            Location.from(data.location),
            data.matterport,
            data.openHouses,
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
    }
}