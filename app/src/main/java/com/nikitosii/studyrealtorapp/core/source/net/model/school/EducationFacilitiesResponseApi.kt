package com.nikitosii.studyrealtorapp.core.source.net.model.school

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class EducationFacilitiesResponseApi(
    val schools: List<SchoolResponseApi>,
    val total: Int? = null,
)