package com.studyrealtorapp.core.source.net.model.photo

import com.google.gson.annotations.SerializedName

data class Photo(
    @SerializedName("href")
    val url: String? = null
)