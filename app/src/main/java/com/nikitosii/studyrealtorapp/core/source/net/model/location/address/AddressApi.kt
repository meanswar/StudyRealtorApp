package com.nikitosii.studyrealtorapp.core.source.net.model.location.address

import com.google.gson.annotations.SerializedName

data class AddressApi(
    @SerializedName("city")
    val city: String? = null,
    @SerializedName("coordinate")
    val coordinate: CoordinateApi? = null,
    @SerializedName("line")
    val line: String? = null,
    @SerializedName("postal_code")
    val postalCode: String? = null,
    @SerializedName("state")
    val state: String? = null,
    @SerializedName("state_code")
    val stateCode: String? = null
)