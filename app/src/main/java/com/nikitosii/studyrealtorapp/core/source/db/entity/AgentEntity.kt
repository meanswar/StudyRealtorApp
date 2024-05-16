package com.nikitosii.studyrealtorapp.core.source.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nikitosii.studyrealtorapp.core.source.db.RealtorDataBase
import com.nikitosii.studyrealtorapp.core.source.local.model.Address
import com.nikitosii.studyrealtorapp.core.source.local.model.Office
import com.nikitosii.studyrealtorapp.core.source.local.model.agent.Agent
import com.nikitosii.studyrealtorapp.core.source.net.model.location.address.AddressResponseApi
import com.nikitosii.studyrealtorapp.core.source.net.model.office.OfficeResponseApi
import com.squareup.moshi.JsonClass

@Entity(tableName = RealtorDataBase.DATABASE_TABLE_AGENT)
data class AgentEntity(
    @PrimaryKey val id: String,
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
    val favorite: Boolean
) {
    companion object {
        fun from(agent: Agent) = AgentEntity(
            id = agent.id,
            fullName = agent.fullName,
            nickname = agent.nickname,
            name = agent.name,
            title = agent.title,
            slogan = agent.slogan,
            photoUrl = agent.photoUrl,
            backgroundPhotoUrl = agent.backgroundPhotoUrl,
            address = agent.address,
            office = agent.office,
            phone = agent.phone,
            webUrl = agent.webUrl,
            recentlySoldCount = agent.recentlySoldCount,
            forSalePriceCount = agent.forSalePriceCount,
            minForSalePrice = agent.minForSalePrice,
            maxForSalePrice = agent.maxForSalePrice,
            reviewCount = agent.reviewCount,
            recommendationsCount = agent.recommendationsCount,
            favorite = agent.favorite
        )
        fun asLocal(agent: Agent) = from(agent).copy(id = agent.id)
    }
}