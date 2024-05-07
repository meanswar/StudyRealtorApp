package com.nikitosii.studyrealtorapp.core.source.local.model.image

import com.nikitosii.studyrealtorapp.core.source.net.model.image.UrlsResponseApi

data class Urls(
    val regular: String? = null
) {
    companion object {
        fun from(response: UrlsResponseApi?): Urls {
            return Urls(response?.regular)
        }
    }
}