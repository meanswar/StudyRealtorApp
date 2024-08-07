package com.nikitosii.studyrealtorapp.core.source.net.model.phone

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PhoneResponseApi(
    val ext: String?,
    val number: String?,
    val primary: Boolean?,
    val trackable: Boolean?,
    val type: String?
)