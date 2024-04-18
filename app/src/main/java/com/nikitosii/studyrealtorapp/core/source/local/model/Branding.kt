package com.nikitosii.studyrealtorapp.core.source.local.model

import com.nikitosii.studyrealtorapp.core.source.net.model.data.BrandingResponseApi

data class Branding(
    val name: String? = null,
    val photo: String? = null,
    val type: String? = null
) {
    companion object {
        fun from(data: BrandingResponseApi?) = Branding(
            data?.name,
            data?.photo,
            data?.type
        )
    }
}