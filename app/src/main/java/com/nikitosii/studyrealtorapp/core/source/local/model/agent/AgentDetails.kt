package com.nikitosii.studyrealtorapp.core.source.local.model.agent

import com.nikitosii.studyrealtorapp.core.source.local.model.Address
import com.nikitosii.studyrealtorapp.core.source.local.model.Phone
import com.nikitosii.studyrealtorapp.core.source.local.model.Photo
import com.nikitosii.studyrealtorapp.core.source.net.model.agent.AgentDetailsResponseApi

data class AgentDetails(
    val firstName: String?,
    val lastName: String?,
    val fullName: String?,
    val description: String?,
    val agentRating: Int?,
    val phone: String?,
    val email: String?,
    val website: String?,
    val photo: Photo?,
    val address: Address?,
    val broker: Broker?,
    val specialization: List<String>?,
    val reviewCount: Int?,
    val recommendationsCount: Int?,
    val averageRating: Float?,
    val reviews: List<Review>,
    val marketingArea: List<Area>,
    val languages: List<String>,
    val servedAreas: List<Area>,
    val phones: List<Phone>
) {
    companion object {
        fun from(data: AgentDetailsResponseApi): AgentDetails = AgentDetails(
            data.firstName,
            data.lastName,
            data.fullName,
            data.description,
            data.agentRating,
            data.phone,
            data.email,
            data.website,
            Photo.from(data.photo),
            Address.from(data.address),
            Broker.from(data.broker),
            data.specialization,
            data.reviewCount,
            data.recommendationsCount,
            data.averageRating,
            reviews = data.reviews?.map { Review.from(it) } ?: listOf(),
            marketingArea = data.marketingArea?.map { Area.from(it) } ?: listOf(),
            languages = data.language ?: listOf(),
            servedAreas = data.servedAreas?.map { Area.from(it) } ?: listOf(),
            phones = data.phones?.map { Phone.from(it) } ?: listOf()
        )
    }
}