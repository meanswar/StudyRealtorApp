package com.studyrealtorapp.core.source.net.model.property

import com.google.gson.annotations.SerializedName

data class OtherResponseApi(
    @SerializedName("listing_id")
    val listingId: String? = null,
    @SerializedName("listing_key")
    val listingKey: String? = null,
    val primary: Boolean? = null,
    val status: String? = null
)