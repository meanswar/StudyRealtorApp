package com.nikitosii.studyrealtorapp.core.source.net.model.flags

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FlagsResponseApi(
    @Json(name = "is_coming_soon")
    val isComingSoon: Boolean? = null,
    @Json(name = "is_contingent")
    val isContingent: Boolean? = null,
    @Json(name = "is_foreclosure")
    val isForeclosure: Boolean? = null,
    @Json(name = "is_new_construction")
    val isNewConstruction: Boolean? = null,
    @Json(name = "is_new_listing")
    val isNewListing: Boolean? = true,
    @Json(name = "is_pending")
    val isPending: Boolean? = null,
    @Json(name = "is_plan")
    val isPlan: Boolean? = null,
    @Json(name = "is_price_reduced")
    val isPriceReduced: Boolean? = null,
    @Json(name = "is_subdivision")
    val isSubdivision: Boolean? = null
)