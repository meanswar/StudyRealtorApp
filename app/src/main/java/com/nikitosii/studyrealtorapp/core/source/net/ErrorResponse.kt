package com.nikitosii.studyrealtorapp.core.source.net

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ErrorResponse(

    @Json(name = "error")
    val error: String? = null,

    @Json(name = "errorMsg")
    val errorMsg: String? = null,

    @Json(name = "status")
    val status: String? = null,

    @Json(name = "externalErrorCode")
    val externalErrorCode: String? = null,

    @Json(name = "externalErrorDescription")
    val externalErrorDescription: String? = null

)