package com.nikitosii.studyrealtorapp.core.source.net.model.agent

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ReviewResponseApi(
    val author: String? = null,
    val description: String? = null,
    val ratingValue: Int? = null
)