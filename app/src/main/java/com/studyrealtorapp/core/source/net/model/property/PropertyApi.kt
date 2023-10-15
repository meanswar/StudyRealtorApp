package com.studyrealtorapp.core.source.net.model.property

import com.google.gson.annotations.SerializedName
import com.studyrealtorapp.core.source.net.model.data.DataApi
import com.studyrealtorapp.core.source.net.model.description.DescriptionApi
import com.studyrealtorapp.core.source.net.model.flags.FlagsApi
import com.studyrealtorapp.core.source.net.model.leadattributes.LeadAttributesApi
import com.studyrealtorapp.core.source.net.model.location.LocationApi
import com.studyrealtorapp.core.source.net.model.photo.Photo
import com.studyrealtorapp.core.source.net.model.product.ProductApi
import com.studyrealtorapp.core.source.net.model.source.SourceApi
import java.util.Objects

data class PropertyApi(
    val branding: List<DataApi>? = null,
    @SerializedName(value = "coming_soon_date")
    val comingSoonDate: String? = null,
    val community: String? = null,
    val description: DescriptionApi? = null,
    val flags: FlagsApi? = null,
    @SerializedName(value = "last_update_date")
    val lastUpdateDate: String? = null,
    @SerializedName(value = "lead_attributes")
    val leadAttributes: LeadAttributesApi? = null,
    @SerializedName("list_date")
    val listDate: String? = null,
    @SerializedName("list_price")
    val listPrice: Int? = null,
    @SerializedName("listing_id")
    val listingId: String? = null,
    val location: LocationApi? = null,
    val matterport: Boolean? = null,
    @SerializedName("open_houses")
    val openHouses: Boolean? = null,
    val rdc: List<Other>? = null,
    val permalink: String? = null,
    val photos: List<Photo>? = null,
    @SerializedName("price_reduced_amount")
    val priceReducedAmount: String? = null,
    @SerializedName("primary_photo")
    val primaryPhoto: Photo,
    val products: ProductApi? = null,
    @SerializedName("property_id")
    val propertyId: String? = null,
    val source: SourceApi? = null,
    val status: String? = null,
    val tags: List<String>? = null,
    @SerializedName("tax_record")
    val taRecord: TaxRecordApi? = null,
    @SerializedName("virtual_tours")
    val virtualTours: List<Object>? = null

    ) {
}