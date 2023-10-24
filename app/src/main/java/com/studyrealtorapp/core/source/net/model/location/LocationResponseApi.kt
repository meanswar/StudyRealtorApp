package com.studyrealtorapp.core.source.net.model.location

import com.google.gson.annotations.SerializedName
import com.studyrealtorapp.core.source.net.model.location.address.AddressApi
import com.studyrealtorapp.core.source.net.model.location.address.CountyApi

data class LocationResponseApi(
    val address: AddressApi? = null,
    val county: CountyApi? = null,
    @SerializedName("street_view_url")
    val streetViewUrl: String? = null,
)