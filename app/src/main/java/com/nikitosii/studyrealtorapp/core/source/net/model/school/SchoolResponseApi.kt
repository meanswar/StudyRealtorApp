package com.nikitosii.studyrealtorapp.core.source.net.model.school

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SchoolResponseApi(
    @Json(name = "id") val id: String?,
    @Json(name = "name") val name: String?,
    @Json(name = "distance_in_miles") val distanceInMiles: Double?,
    @Json(name = "education_levels") val educationLevels: List<String>?,
    @Json(name = "funding_type") val fundingType: String?,
    @Json(name = "grades") val grades: List<String>?,
    @Json(name = "student_count") val studentCount: Int?,
    @Json(name = "student_teacher_ratio") val studentTeacherRatio: Double?,
    @Json(name = "parent_rating") val parentRating: Int?,
    @Json(name = "rating") val rating: Int?,
    @Json(name = "review_count") val reviewCount: Int?,
    @Json(name = "slug") val slug: String?,
    @Json(name = "slug_id") val slugId: String?
)