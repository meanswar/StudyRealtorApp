package com.nikitosii.studyrealtorapp.core.source.net.model.vrtour

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class VrTourResponseApi(
    val href: String? = null,
    val type: String? = null
)