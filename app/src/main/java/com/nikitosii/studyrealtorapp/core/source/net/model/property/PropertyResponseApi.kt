package com.nikitosii.studyrealtorapp.core.source.net.model.property

import com.nikitosii.studyrealtorapp.core.source.net.model.commnunity.CommunityResponseApi
import com.nikitosii.studyrealtorapp.core.source.net.model.data.BrandingResponseApi
import com.nikitosii.studyrealtorapp.core.source.net.model.description.DescriptionResponseApi
import com.nikitosii.studyrealtorapp.core.source.net.model.flags.FlagsResponseApi
import com.nikitosii.studyrealtorapp.core.source.net.model.house.HouseDescriptionResponseAPI
import com.nikitosii.studyrealtorapp.core.source.net.model.leadattributes.LeadAttributesResponseApi
import com.nikitosii.studyrealtorapp.core.source.net.model.location.LocationResponseApi
import com.nikitosii.studyrealtorapp.core.source.net.model.photo.PhotoResponseApi
import com.nikitosii.studyrealtorapp.core.source.net.model.product.ProductResponseApi
import com.nikitosii.studyrealtorapp.core.source.net.model.source.SourceResponseApi
import com.nikitosii.studyrealtorapp.core.source.net.model.vrtour.VrTourResponseApi
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class PropertyResponseApi(
    val branding: List<BrandingResponseApi>? = null,
    @Json(name = "coming_soon_date")
    val comingSoonDate: String? = null,
    val community: CommunityResponseApi? = null,
    val description: DescriptionResponseApi? = null,
    val flags: FlagsResponseApi? = null,
    @Json(name = "last_update_date")
    val lastUpdateDate: String? = null,
    @Json(name = "lead_attributes")
    val leadAttributes: LeadAttributesResponseApi? = null,
    @Json(name = "list_date")
    val listDate: String? = null,
    @Json(name = "list_price")
    val listPrice: Int? = null,
    @Json(name = "listing_id")
    val listingId: String? = null,
    val location: LocationResponseApi? = null,
    val matterport: Boolean? = null,
    @Json(name = "open_houses")
    val openHouses: List<HouseDescriptionResponseAPI>? = null,
    val rdc: List<OtherResponseApi>? = null,
    val permalink: String? = null,
    val photoResponseApis: List<PhotoResponseApi>? = null,
    @Json(name = "price_reduced_amount")
    val priceReducedAmount: String? = null,
    @Json(name = "primary_photo")
    val primaryPhotoResponseApi: PhotoResponseApi,
    val products: ProductResponseApi? = null,
    @Json(name = "property_id")
    val propertyId: String? = null,
    val source: SourceResponseApi? = null,
    val status: String? = null,
    val tags: List<String>? = null,
    @Json(name = "tax_record")
    val taxRecord: TaxRecordResponseApi? = null,
    @Json(name = "virtual_tours")
    val virtualTours: List<VrTourResponseApi>? = null
)