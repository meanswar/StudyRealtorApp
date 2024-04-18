package com.nikitosii.studyrealtorapp.core.source.local.model

import com.nikitosii.studyrealtorapp.core.source.net.model.commnunity.CommunityResponseApi

data class Community(
    val advertisers: List<Any>? = null,
    val description: CommunityDescription? = null,
    val propertyId: String? = null
) {
    companion object {
        fun from(response: CommunityResponseApi?) = Community(
            advertisers = response?.advertises,
            description = CommunityDescription.from(response?.description),
            propertyId = response?.propertyId
        )
    }
}