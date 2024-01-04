package com.studyrealtorapp.core.source.local.model

import com.studyrealtorapp.core.source.net.model.location.address.CountyResponseApi

data class County(
    val fipsCode: String? = null,
    val name: String? = null
) {
    companion object {
        fun from(data: CountyResponseApi?): County = County(
            data?.fipsCode,
            data?.name
        )
    }
}