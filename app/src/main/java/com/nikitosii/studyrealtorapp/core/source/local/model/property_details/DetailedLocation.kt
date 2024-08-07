package com.nikitosii.studyrealtorapp.core.source.local.model.property_details

import com.nikitosii.studyrealtorapp.core.source.local.model.Address
import com.nikitosii.studyrealtorapp.core.source.local.model.County
import com.nikitosii.studyrealtorapp.core.source.net.model.location.DetailedLocationResponseApi

data class DetailedLocation(
    val address: Address? = null,
    val country: String? = null,
    val line: String? = null,
    val state: String? = null,
    val stateCode: String? = null,
    val county: County? = null
) {
    companion object {
        fun from(source: DetailedLocationResponseApi?) = DetailedLocation(
            source?.address?.let { Address.from(it) },
            source?.country,
            source?.line,
            source?.state,
            source?.stateCode,
            source?.county?.let { County.from(it) }
        )
    }
}