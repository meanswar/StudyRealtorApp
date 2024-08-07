package com.nikitosii.studyrealtorapp.core.source.net.model.property

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TaxRecordResponseApi(
    @Json(name = "public_record_id")
    val publicRecordId: String? = null
)