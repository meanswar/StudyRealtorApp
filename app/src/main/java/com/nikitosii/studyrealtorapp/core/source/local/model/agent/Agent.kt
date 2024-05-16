package com.nikitosii.studyrealtorapp.core.source.local.model.agent

import com.nikitosii.studyrealtorapp.core.source.db.entity.AgentEntity
import com.nikitosii.studyrealtorapp.core.source.local.model.Address
import com.nikitosii.studyrealtorapp.core.source.local.model.Office
import com.nikitosii.studyrealtorapp.core.source.net.model.agent.AgentResponseApi
import com.nikitosii.studyrealtorapp.core.source.net.model.location.address.AddressResponseApi
import com.nikitosii.studyrealtorapp.core.source.net.model.office.OfficeResponseApi

data class Agent(
    val id: String,
    val fullName: String?,
    val nickname: String?,
    val name: String?,
    val title: String?,
    val slogan: String?,
    val photoUrl: String?,
    val backgroundPhotoUrl: String?,
    val address: Address?,
    val office: Office?,
    val phone: String?,
    val webUrl: String?,
    val recentlySoldCount: Int?,
    val forSalePriceCount: Int?,
    val minForSalePrice: Int?,
    val maxForSalePrice: Int?,
    val reviewCount: Int?,
    val recommendationsCount: Int?,
    val favorite: Boolean = false
) {
    companion object {
        fun from(api: AgentResponseApi) = Agent(
            fullName = api.fullName,
            nickname = api.nickname,
            name = api.name,
            title = api.title,
            slogan = api.slogan,
            photoUrl = api.photoUrl,
            backgroundPhotoUrl = api.backgroundPhotoUrl,
            address = Address.from(api.address),
            office = Office.from(api.office),
            phone = api.phone,
            webUrl = api.webUrl,
            recentlySoldCount = api.recentlySoldCount,
            forSalePriceCount = api.forSalePriceCount,
            minForSalePrice = api.minForSalePrice,
            maxForSalePrice = api.maxForSalePrice,
            reviewCount = api.reviewCount,
            recommendationsCount = api.recommendationsCount,
            id = api.id
        )

        fun from(entity: AgentEntity) = Agent(
            id = entity.id,
            fullName = entity.fullName,
            nickname = entity.nickname,
            name = entity.name,
            title = entity.title,
            slogan = entity.slogan,
            photoUrl = entity.photoUrl,
            backgroundPhotoUrl = entity.backgroundPhotoUrl,
            address = entity.address,
            office = entity.office,
            phone = entity.phone,
            webUrl = entity.webUrl,
            recentlySoldCount = entity.recentlySoldCount,
            forSalePriceCount = entity.forSalePriceCount,
            minForSalePrice = entity.minForSalePrice,
            maxForSalePrice = entity.maxForSalePrice,
            reviewCount = entity.reviewCount,
            recommendationsCount = entity.recommendationsCount,
            favorite = entity.favorite
        )
    }
}