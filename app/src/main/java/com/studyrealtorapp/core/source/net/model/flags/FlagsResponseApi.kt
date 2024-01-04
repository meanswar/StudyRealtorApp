package com.studyrealtorapp.core.source.net.model.flags

import com.google.gson.annotations.SerializedName

data class FlagsResponseApi(
    @SerializedName("is_coming_soon")
    val isComingSoon: Boolean? = null,
    @SerializedName("is_contingent")
    val isContingent: Boolean? = null,
    @SerializedName("is_foreclosure")
    val isForeclosure: Boolean? = null,
    @SerializedName("is_new_construction")
    val isNewConstruction: Boolean? = null,
    @SerializedName("is_new_listing")
    val isNewListing: Boolean? = true,
    @SerializedName("is_pending")
    val isPending: Boolean? = null,
    @SerializedName("is_plan")
    val isPlan: Boolean? = null,
    @SerializedName("is_price_reduced")
    val isPriceReduced: Boolean? = null,
    @SerializedName("is_subdivision")
    val isSubdivision: Boolean? = null
)