package com.nikitosii.studyrealtorapp.core.source.net.model.product

import com.google.gson.annotations.SerializedName
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProductResponseApi(
    @SerializedName("brand_name")
    val brandName: String? = null,
    val product: List<String>? = null
)