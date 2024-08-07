package com.nikitosii.studyrealtorapp.core.source.net.model.agent

import com.nikitosii.studyrealtorapp.core.source.net.model.photo.PhotoResponseApi
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class BrokerResponseApi(
    val name: String,
    val photo: PhotoResponseApi
)