package com.studyrealtorapp.core.source.local.model

import com.studyrealtorapp.core.source.net.model.location.LocationResponseApi
import com.studyrealtorapp.core.source.net.model.location.address.AddressApi

data class Location(
    val address: AddressApi? = null,
    val county: County? = null,
    val streetViewUrl: String? = null,
) {
    companion object {
        fun from(data: LocationResponseApi?): Location = Location(
            data?.address,
            County.from(data?.county),
            data?.streetViewUrl
        )
    }
}