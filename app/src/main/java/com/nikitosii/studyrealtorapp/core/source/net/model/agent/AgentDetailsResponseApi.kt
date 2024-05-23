package com.nikitosii.studyrealtorapp.core.source.net.model.agent

import com.nikitosii.studyrealtorapp.core.source.net.model.location.address.AddressResponseApi
import com.nikitosii.studyrealtorapp.core.source.net.model.phone.PhoneResponseApi
import com.nikitosii.studyrealtorapp.core.source.net.model.photo.PhotoResponseApi
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AgentDetailsResponseApi(
    @Json(name = "first_name")
    val firstName: String? = null,
    @Json(name = "last_name")
    val lastName: String? = null,
    @Json(name = "full_name")
    val fullName: String? = null,
    val description: String? = null,
    @Json(name = "agent_rating")
    val agentRating: Int? = null,
    val phone: String? = null,
    val email: String? = null,
    @Json(name = "web_url")
    val website: String? = null,
    val photo: PhotoResponseApi? = null,
    val address: AddressResponseApi? = null,
    val broker: BrokerResponseApi? = null,
    val specialization: List<String>? = null,
    @Json(name = "user_languages")
    val language: List<String>? = null,
    @Json(name = "review_count")
    val reviewCount: Int? = null,
    @Json(name = "recommendations_count")
    val recommendationsCount: Int? = null,
    @Json(name = "average_rating")
    val averageRating: Float? = null,
    val reviews: List<ReviewResponseApi>? = null,
    @Json(name = "marketing_area_cities")
    val marketingArea: List<AreaResponseApi>? = null,
    @Json(name = "served_areas")
    val servedAreas: List<AreaResponseApi>? = null,
    val phones: List<PhoneResponseApi>? = null
)