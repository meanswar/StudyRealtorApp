package com.nikitosii.studyrealtorapp.core.source.net.model.property

import com.google.gson.annotations.SerializedName
import com.nikitosii.studyrealtorapp.core.source.net.model.data.DataResponseApi
import com.nikitosii.studyrealtorapp.core.source.net.model.description.DescriptionResponseApi
import com.nikitosii.studyrealtorapp.core.source.net.model.flags.FlagsResponseApi
import com.nikitosii.studyrealtorapp.core.source.net.model.leadattributes.LeadAttributesResponseApi
import com.nikitosii.studyrealtorapp.core.source.net.model.location.LocationResponseApi
import com.nikitosii.studyrealtorapp.core.source.net.model.photo.PhotoResponseApi
import com.nikitosii.studyrealtorapp.core.source.net.model.product.ProductResponseApi
import com.nikitosii.studyrealtorapp.core.source.net.model.source.SourceResponseApi

data class PropertyResponseApi(
    val branding: List<DataResponseApi>? = null,
    @SerializedName(value = "coming_soon_date")
    val comingSoonDate: String? = null,
    val community: String? = null,
    val description: DescriptionResponseApi? = null,
    val flags: FlagsResponseApi? = null,
    @SerializedName(value = "last_update_date")
    val lastUpdateDate: String? = null,
    @SerializedName(value = "lead_attributes")
    val leadAttributes: LeadAttributesResponseApi? = null,
    @SerializedName("list_date")
    val listDate: String? = null,
    @SerializedName("list_price")
    val listPrice: Int? = null,
    @SerializedName("listing_id")
    val listingId: String? = null,
    val location: LocationResponseApi? = null,
    val matterport: Boolean? = null,
    @SerializedName("open_houses")
    val openHouses: Boolean? = null,
    val rdc: List<OtherResponseApi>? = null,
    val permalink: String? = null,
    val photoResponseApis: List<PhotoResponseApi>? = null,
    @SerializedName("price_reduced_amount")
    val priceReducedAmount: String? = null,
    @SerializedName("primary_photo")
    val primaryPhotoResponseApi: PhotoResponseApi,
    val products: ProductResponseApi? = null,
    @SerializedName("property_id")
    val propertyId: String? = null,
    val source: SourceResponseApi? = null,
    val status: String? = null,
    val tags: List<String>? = null,
    @SerializedName("tax_record")
    val taxRecord: TaxRecordResponseApi? = null,
    @SerializedName("virtual_tours")
    val virtualTours: List<Object>? = null
)