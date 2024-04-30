package com.nikitosii.studyrealtorapp.core.source.net.model.property

import com.nikitosii.studyrealtorapp.core.source.net.model.data.BrandingResponseApi
import com.nikitosii.studyrealtorapp.core.source.net.model.description.DescriptionResponseApi
import com.nikitosii.studyrealtorapp.core.source.net.model.location.DetailedLocationResponseApi
import com.nikitosii.studyrealtorapp.core.source.net.model.photo.PhotoResponseApi
import com.nikitosii.studyrealtorapp.core.source.net.model.school.EducationFacilitiesResponseApi
import com.nikitosii.studyrealtorapp.core.source.net.model.source.SourceResponseApi
import com.nikitosii.studyrealtorapp.core.source.net.model.vrtour.VrTourResponseApi
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PropertyDetailsResponseApi(
    val branding: List<BrandingResponseApi>? = null,
    @Json(name = "coming_soon_date")
    val comingSoonDate: String? = null,
    val description: DescriptionResponseApi? = null,
    @Json(name = "last_update_date")
    val lastUpdateDate: String? = null,
    @Json(name = "list_date")
    val listDate: String? = null,
    @Json(name = "list_price")
    val listPrice: Int? = null,
    @Json(name = "listing_id")
    val listingId: String? = null,
    val location: DetailedLocationResponseApi? = null,
    val photoResponseApi: List<PhotoResponseApi>? = null,
    val schools: EducationFacilitiesResponseApi? = null,
    val source: SourceResponseApi? = null,
    val propertyId: String? = null,
    val status: String? = null,
    val tags: List<String>? = null,
    @Json(name = "virtual_tours")
    val virtualTours: List<VrTourResponseApi>? = null
)