package com.nikitosii.studyrealtorapp.core.source.local.model

import com.nikitosii.studyrealtorapp.core.source.net.model.commnunity.CommunityDescriptionResponseApi

data class CommunityDescription(
    val name: String? = null
) {
    companion object {
        fun from(response: CommunityDescriptionResponseApi?) = CommunityDescription(
            name = response?.name
        )
    }
}