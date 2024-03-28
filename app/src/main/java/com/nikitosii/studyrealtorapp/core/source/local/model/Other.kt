package com.nikitosii.studyrealtorapp.core.source.local.model

import com.nikitosii.studyrealtorapp.core.source.net.model.property.OtherResponseApi

data class Other(
    val listingId: String? = null,
    val listingKey: String? = null,
    val primary: Boolean? = null,
    val status: String? = null
) {

    companion object {
        fun from(data: OtherResponseApi?): Other = Other(
            listingId = data?.listingId,
            listingKey = data?.listingKey,
            primary = data?.primary,
            status = data?.status
        )
    }
}