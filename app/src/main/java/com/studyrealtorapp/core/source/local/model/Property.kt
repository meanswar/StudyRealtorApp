package com.studyrealtorapp.core.source.local.model

import com.studyrealtorapp.core.source.net.model.data.DataResponseApi
import com.studyrealtorapp.core.source.net.model.description.DescriptionResponseApi
import com.studyrealtorapp.core.source.net.model.flags.FlagsApi
import com.studyrealtorapp.core.source.net.model.leadattributes.LeadAttributesResponseApi
import com.studyrealtorapp.core.source.net.model.location.LocationResponseApi
import com.studyrealtorapp.core.source.net.model.photo.PhotoResponseApi
import com.studyrealtorapp.core.source.net.model.product.ProductResponseApi
import com.studyrealtorapp.core.source.net.model.property.OtherResponseApi
import com.studyrealtorapp.core.source.net.model.property.TaxRecordResponseApi
import com.studyrealtorapp.core.source.net.model.source.SourceResponseApi

data class Property(
    val branding: List<DataResponseApi>? = null,
    val comingSoonDate: String? = null,
    val community: String? = null,
    val description: DescriptionResponseApi? = null,
    val flags: FlagsApi? = null,
    val lastUpdateDate: String? = null,
    val leadAttributes: LeadAttributesResponseApi? = null,
    val listDate: String? = null,
    val listPrice: Int? = null,
    val listingId: String? = null,
    val location: LocationResponseApi? = null,
    val matterport: Boolean? = null,
    val openHouses: Boolean? = null,
    val rdc: List<OtherResponseApi>? = null,
    val permalink: String? = null,
    val photoResponseApis: List<PhotoResponseApi>? = null,
    val priceReducedAmount: String? = null,
    val primaryPhotoResponseApi: PhotoResponseApi,
    val products: ProductResponseApi? = null,
    val propertyId: String? = null,
    val source: SourceResponseApi? = null,
    val status: String? = null,
    val tags: List<String>? = null,
    val taRecord: TaxRecordResponseApi? = null,
    val virtualTours: List<Object>? = null
)