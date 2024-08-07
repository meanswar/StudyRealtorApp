package com.nikitosii.studyrealtorapp.core.source.net.model.base

import com.google.gson.annotations.SerializedName
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BaseResponseApi<T>(
    @SerializedName(value = "status")
    val results: List<T>
)