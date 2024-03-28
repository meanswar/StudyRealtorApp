package com.nikitosii.studyrealtorapp.core.source.net.model.base

import com.google.gson.annotations.SerializedName

data class BaseResponseApi<T>(
    @SerializedName(value = "status")
    val results: List<T>
)