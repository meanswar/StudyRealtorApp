package com.nikitosii.studyrealtorapp.core.source.net.model.house

import com.squareup.moshi.Json

data class HouseDescriptionResponseAPI(
    @Json(name = "description")
    val description: String? = null,
    @Json(name = "dst")
    val dst: Boolean? = null,
    @Json(name = "end_date")
    val endDate: String? = null,
    @Json(name="methods")
    val methods: List<String>? = null,
    @Json(name = "start_date")
    val startDate: String? = null,
    @Json(name = "time_zone")
    val timeZone: String? = null
)
