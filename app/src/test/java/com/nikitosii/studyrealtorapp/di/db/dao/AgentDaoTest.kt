package com.nikitosii.studyrealtorapp.di.db.dao

import com.nikitosii.studyrealtorapp.core.source.db.dao.AgentDao
import com.nikitosii.studyrealtorapp.core.source.db.entity.AgentEntity
import com.nikitosii.studyrealtorapp.core.source.local.model.Address
import com.nikitosii.studyrealtorapp.core.source.local.model.Coordinate
import com.nikitosii.studyrealtorapp.core.source.local.model.Office
import com.nikitosii.studyrealtorapp.core.source.local.model.Phone
import com.nikitosii.studyrealtorapp.core.source.local.model.agent.SalePrice
import com.nikitosii.studyrealtorapp.util.TestConstants

object AgentDaoTest : AgentDao {

    private val agentList = mutableListOf(
        AgentEntity(
            id = TestConstants.ID_VALID_TEXT,
            fullName = TestConstants.NAME_VALID,
            nickname = TestConstants.EMPTY_TEXT,
            name = TestConstants.NAME_VALID,
            title = TestConstants.ANY_TEXT,
            slogan = TestConstants.ANY_TEXT,
            photoUrl = TestConstants.PHOTO_VALID,
            backgroundPhotoUrl = TestConstants.PHOTO_VALID,
            address = Address(
                city = TestConstants.LOCATION_VALID,
                coordinate = Coordinate(
                    latitude = TestConstants.LATITUDE_VALID,
                    longitude = TestConstants.LONGITUDE_VALID
                ),
                line = TestConstants.ADDRESS_VALID,
                postalCode = TestConstants.ANY_TEXT,
                state = TestConstants.ANY_TEXT,
                stateCode = TestConstants.ANY_TEXT
            ),
            office = Office(
                email = TestConstants.EMAIL_VALID,
                id = TestConstants.ID_VALID_TEXT,
                href = TestConstants.URL_VALID,
                mls_set = TestConstants.STATUS_FOR_SALE,
                name = TestConstants.NAME_VALID,
                phones = listOf(
                    Phone(
                        ext = TestConstants.PHONE_EXT_VALID,
                        number = TestConstants.PHONE_VALID,
                        primary = TestConstants.BOOLEAN_VALID,
                        trackable = TestConstants.BOOLEAN_VALID,
                        type = TestConstants.ANY_TEXT
                    )
                ),
                image = TestConstants.IMAGE_VALID
            ),
            phone = TestConstants.PHONE_VALID,
            webUrl = TestConstants.URL_VALID,
            recentlySoldCount = TestConstants.ANY_DIGITS,
            forSalePriceCount = TestConstants.ANY_DIGITS,
            minForSalePrice = TestConstants.PRICE_VALID,
            maxForSalePrice = TestConstants.ANY_DIGITS,
            reviewCount = TestConstants.ANY_DIGITS,
            recommendationsCount = TestConstants.ANY_DIGITS,
            favorite = TestConstants.BOOLEAN_VALID,
            salePrice = SalePrice(
                min = TestConstants.PRICE_VALID,
                max = TestConstants.ANY_DIGITS,
                lastListingDate = TestConstants.SERVER_DATE_PATTERN
            )
        ),
        AgentEntity(
            id = TestConstants.ID_VALID_TEXT,
            fullName = TestConstants.NAME_VALID,
            nickname = TestConstants.EMPTY_TEXT,
            name = TestConstants.NAME_VALID,
            title = TestConstants.ANY_TEXT,
            slogan = TestConstants.ANY_TEXT,
            photoUrl = TestConstants.PHOTO_VALID,
            backgroundPhotoUrl = TestConstants.PHOTO_VALID,
            address = Address(
                city = TestConstants.LOCATION_VALID,
                coordinate = Coordinate(
                    latitude = TestConstants.LATITUDE_VALID,
                    longitude = TestConstants.LONGITUDE_VALID
                ),
                line = TestConstants.ADDRESS_VALID,
                postalCode = TestConstants.ANY_TEXT,
                state = TestConstants.ANY_TEXT,
                stateCode = TestConstants.ANY_TEXT
            ),
            office = Office(
                email = TestConstants.EMAIL_VALID,
                id = TestConstants.ID_VALID_TEXT,
                href = TestConstants.URL_VALID,
                mls_set = TestConstants.STATUS_FOR_SALE,
                name = TestConstants.NAME_VALID,
                phones = listOf(
                    Phone(
                        ext = TestConstants.PHONE_EXT_VALID,
                        number = TestConstants.PHONE_VALID,
                        primary = TestConstants.BOOLEAN_VALID,
                        trackable = TestConstants.BOOLEAN_VALID,
                        type = TestConstants.ANY_TEXT
                    )
                ),
                image = TestConstants.IMAGE_VALID
            ),
            phone = TestConstants.PHONE_VALID,
            webUrl = TestConstants.URL_VALID,
            recentlySoldCount = TestConstants.ANY_DIGITS,
            forSalePriceCount = TestConstants.ANY_DIGITS,
            minForSalePrice = TestConstants.PRICE_VALID,
            maxForSalePrice = TestConstants.ANY_DIGITS,
            reviewCount = TestConstants.ANY_DIGITS,
            recommendationsCount = TestConstants.ANY_DIGITS,
            favorite = TestConstants.BOOLEAN_INVALID,
            salePrice = SalePrice(
                min = TestConstants.PRICE_VALID,
                max = TestConstants.ANY_DIGITS,
                lastListingDate = TestConstants.SERVER_DATE_PATTERN
            )
        ),
        AgentEntity(
            id = TestConstants.ID_VALID_TEXT,
            fullName = TestConstants.NAME_VALID,
            nickname = TestConstants.EMPTY_TEXT,
            name = TestConstants.NAME_VALID,
            title = TestConstants.ANY_TEXT,
            slogan = TestConstants.ANY_TEXT,
            photoUrl = TestConstants.PHOTO_VALID,
            backgroundPhotoUrl = TestConstants.PHOTO_VALID,
            address = Address(
                city = TestConstants.LOCATION_VALID,
                coordinate = Coordinate(
                    latitude = TestConstants.LATITUDE_VALID,
                    longitude = TestConstants.LONGITUDE_VALID
                ),
                line = TestConstants.ADDRESS_VALID,
                postalCode = TestConstants.ANY_TEXT,
                state = TestConstants.ANY_TEXT,
                stateCode = TestConstants.ANY_TEXT
            ),
            office = Office(
                email = TestConstants.EMAIL_VALID,
                id = TestConstants.ID_VALID_TEXT,
                href = TestConstants.URL_VALID,
                mls_set = TestConstants.STATUS_FOR_SALE,
                name = TestConstants.NAME_VALID,
                phones = listOf(
                    Phone(
                        ext = TestConstants.PHONE_EXT_VALID,
                        number = TestConstants.PHONE_VALID,
                        primary = TestConstants.BOOLEAN_VALID,
                        trackable = TestConstants.BOOLEAN_VALID,
                        type = TestConstants.ANY_TEXT
                    )
                ),
                image = TestConstants.IMAGE_VALID
            ),
            phone = TestConstants.PHONE_VALID,
            webUrl = TestConstants.URL_VALID,
            recentlySoldCount = TestConstants.ANY_DIGITS,
            forSalePriceCount = TestConstants.ANY_DIGITS,
            minForSalePrice = TestConstants.PRICE_VALID,
            maxForSalePrice = TestConstants.ANY_DIGITS,
            reviewCount = TestConstants.ANY_DIGITS,
            recommendationsCount = TestConstants.ANY_DIGITS,
            favorite = TestConstants.BOOLEAN_VALID,
            salePrice = SalePrice(
                min = TestConstants.PRICE_VALID,
                max = TestConstants.ANY_DIGITS,
                lastListingDate = TestConstants.SERVER_DATE_PATTERN
            )
        ),
        AgentEntity(
            id = TestConstants.ID_VALID_TEXT,
            fullName = TestConstants.NAME_VALID,
            nickname = TestConstants.EMPTY_TEXT,
            name = TestConstants.NAME_VALID,
            title = TestConstants.ANY_TEXT,
            slogan = TestConstants.ANY_TEXT,
            photoUrl = TestConstants.PHOTO_VALID,
            backgroundPhotoUrl = TestConstants.PHOTO_VALID,
            address = Address(
                city = TestConstants.LOCATION_VALID,
                coordinate = Coordinate(
                    latitude = TestConstants.LATITUDE_VALID,
                    longitude = TestConstants.LONGITUDE_VALID
                ),
                line = TestConstants.ADDRESS_VALID,
                postalCode = TestConstants.ANY_TEXT,
                state = TestConstants.ANY_TEXT,
                stateCode = TestConstants.ANY_TEXT
            ),
            office = Office(
                email = TestConstants.EMAIL_VALID,
                id = TestConstants.ID_VALID_TEXT,
                href = TestConstants.URL_VALID,
                mls_set = TestConstants.STATUS_FOR_SALE,
                name = TestConstants.NAME_VALID,
                phones = listOf(
                    Phone(
                        ext = TestConstants.PHONE_EXT_VALID,
                        number = TestConstants.PHONE_VALID,
                        primary = TestConstants.BOOLEAN_VALID,
                        trackable = TestConstants.BOOLEAN_VALID,
                        type = TestConstants.ANY_TEXT
                    )
                ),
                image = TestConstants.IMAGE_VALID
            ),
            phone = TestConstants.PHONE_VALID,
            webUrl = TestConstants.URL_VALID,
            recentlySoldCount = TestConstants.ANY_DIGITS,
            forSalePriceCount = TestConstants.ANY_DIGITS,
            minForSalePrice = TestConstants.PRICE_VALID,
            maxForSalePrice = TestConstants.ANY_DIGITS,
            reviewCount = TestConstants.ANY_DIGITS,
            recommendationsCount = TestConstants.ANY_DIGITS,
            favorite = TestConstants.BOOLEAN_INVALID,
            salePrice = SalePrice(
                min = TestConstants.PRICE_VALID,
                max = TestConstants.ANY_DIGITS,
                lastListingDate = TestConstants.SERVER_DATE_PATTERN
            )
        )
    )

    override fun getLocalAgents(id: List<String>): List<AgentEntity> =
        agentList.filter { it.id in id }


    override fun getLocalAgents(): List<AgentEntity> = agentList

    override fun getFavoriteAgents(): List<AgentEntity> = agentList.filter { it.favorite }

    override fun getFavoriteAgentsListFromList(ids: List<String>): List<AgentEntity> =
        agentList.filter { it.id in ids && it.favorite }

    override fun getRecentFavoriteAgents(): List<AgentEntity> = agentList
        .filter { it.favorite }
        .takeLast(3)

    override fun insertAgents(list: List<AgentEntity>) {
        agentList.addAll(list)
    }

    override fun insertAgent(agent: AgentEntity) {
        agentList.add(agent)
    }

    override fun deleteAllAgents() {
        agentList.removeAll { true }
    }

    override fun deleteAgent(id: String) {
        agentList.removeIf { it.id == id }
    }
}