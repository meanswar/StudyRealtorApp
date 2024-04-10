package com.nikitosii.studyrealtorapp.core.source.net.model.base

import com.google.gson.annotations.SerializedName
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BaseSalesResponseApi<T>(
    @SerializedName("home_search")
    val homeSearch: Int,
    @SerializedName("results")
    val results: List<T>,
    @SerializedName("total")
    val total: Int
)