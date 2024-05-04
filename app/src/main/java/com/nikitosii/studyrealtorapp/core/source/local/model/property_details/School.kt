package com.nikitosii.studyrealtorapp.core.source.local.model.property_details

import com.nikitosii.studyrealtorapp.core.source.net.model.school.SchoolResponseApi

data class School(
    val id: String?,
    val name: String?,
    val distanceInMiles: Double?,
    val educationLevels: List<String>?,
    val fundingType: String?,
    val grades: List<String>?,
    val studentCount: Int?,
    val studentTeacherRatio: Double?,
    val parentRating: Int?,
    val rating: Int?,
    val reviewCount: Int?,
    val slug: String?,
    val slugId: String?
) {

    override fun toString(): String {
        return """
            Name: $name
            Distance: $distanceInMiles miles
            Funding Type: $fundingType
            Grades: ${grades?.joinToString()}
            Student Count: $studentCount
            Student-Teacher Ratio: ${studentTeacherRatio ?: "N/A"}
            Parent Rating: ${parentRating ?: "N/A"}
            Rating: ${rating ?: "N/A"}
            Review Count: $reviewCount
            Slug: $slug
            Slug ID: $slugId
            -----------------------------------------------------
        """.trimIndent()
    }
    companion object {
        fun from(source: SchoolResponseApi?) = School(
            source?.id,
            source?.name,
            source?.distanceInMiles,
            source?.educationLevels,
            source?.fundingType,
            source?.grades,
            source?.studentCount ,
            source?.studentTeacherRatio,
            source?.parentRating,
            source?.rating,
            source?.reviewCount,
            source?.slug,
            source?.slugId
        )
    }
}