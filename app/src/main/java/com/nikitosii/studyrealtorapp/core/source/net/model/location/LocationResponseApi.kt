package com.nikitosii.studyrealtorapp.core.source.net.model.location

import com.google.gson.annotations.SerializedName
import com.nikitosii.studyrealtorapp.core.source.net.model.location.address.AddressApi
import com.nikitosii.studyrealtorapp.core.source.net.model.location.address.CountyResponseApi

data class LocationResponseApi(
    val address: AddressApi? = null,
    val county: CountyResponseApi? = null,
    @SerializedName("street_view_url")
    val streetViewUrl: String? = null,
)