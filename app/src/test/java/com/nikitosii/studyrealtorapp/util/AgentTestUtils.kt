package com.nikitosii.studyrealtorapp.util

import com.nikitosii.studyrealtorapp.core.source.db.entity.AgentEntity
import com.nikitosii.studyrealtorapp.core.source.local.model.Address
import com.nikitosii.studyrealtorapp.core.source.local.model.Coordinate
import com.nikitosii.studyrealtorapp.core.source.local.model.Office
import com.nikitosii.studyrealtorapp.core.source.local.model.Phone
import com.nikitosii.studyrealtorapp.core.source.local.model.agent.Agent
import com.nikitosii.studyrealtorapp.core.source.local.model.agent.AgentRequestApi
import com.nikitosii.studyrealtorapp.core.source.local.model.agent.SalePrice
import com.nikitosii.studyrealtorapp.core.source.net.model.agent.AgentDetailsResponseApi
import com.nikitosii.studyrealtorapp.core.source.net.model.agent.AgentResponseApi
import com.nikitosii.studyrealtorapp.core.source.net.model.agent.AreaResponseApi
import com.nikitosii.studyrealtorapp.core.source.net.model.agent.BrokerResponseApi
import com.nikitosii.studyrealtorapp.core.source.net.model.agent.ReviewResponseApi
import com.nikitosii.studyrealtorapp.core.source.net.model.agent.SalePriceResponseApi
import com.nikitosii.studyrealtorapp.core.source.net.model.base.BaseAgentDetailsResponse
import com.nikitosii.studyrealtorapp.core.source.net.model.base.BaseAgentsSearch
import com.nikitosii.studyrealtorapp.core.source.net.model.location.address.AddressResponseApi
import com.nikitosii.studyrealtorapp.core.source.net.model.location.address.CoordinateApi
import com.nikitosii.studyrealtorapp.core.source.net.model.office.OfficeResponseApi
import com.nikitosii.studyrealtorapp.core.source.net.model.phone.PhoneResponseApi
import com.nikitosii.studyrealtorapp.core.source.net.model.photo.PhotoResponseApi

object AgentTestUtils {
    fun getExpectedAgentsList(): List<AgentEntity> = listOf(
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
        )
    )

    fun getExpectedLocalAgents(): List<Agent> = getExpectedAgentsList().map { Agent.from(it) }

    fun getAgentRequest(): AgentRequestApi = AgentRequestApi(
        TestConstants.ANY_TEXT,
        TestConstants.ANY_TEXT,
        TestConstants.BOOLEAN_VALID,
        TestConstants.ANY_DIGITS,
        TestConstants.ANY_TEXT,
        TestConstants.ANY_TEXT
    )

    fun getAgentsFromNetwork(): BaseAgentsSearch = BaseAgentsSearch(
        listOf(
            AgentResponseApi(
                id = TestConstants.ID_VALID_TEXT,
                fullName = TestConstants.NAME_VALID,
                nickname = TestConstants.EMPTY_TEXT,
                name = TestConstants.NAME_VALID,
                title = TestConstants.ANY_TEXT,
                slogan = TestConstants.ANY_TEXT,
                photoUrl = PhotoResponseApi(TestConstants.PHOTO_VALID),
                backgroundPhotoUrl = TestConstants.PHOTO_VALID,
                address = AddressResponseApi(
                    city = TestConstants.LOCATION_VALID,
                    coordinate = CoordinateApi(
                        lat = TestConstants.LATITUDE_VALID,
                        lon = TestConstants.LONGITUDE_VALID
                    ),
                    line = TestConstants.ADDRESS_VALID,
                    postalCode = TestConstants.ANY_TEXT,
                    state = TestConstants.ANY_TEXT,
                    stateCode = TestConstants.ANY_TEXT
                ),
                office = OfficeResponseApi(
                    email = TestConstants.EMAIL_VALID,
                    fulfillment_id = TestConstants.ID_VALID_TEXT,
                    href = TestConstants.URL_VALID,
                    mls_set = TestConstants.STATUS_FOR_SALE,
                    name = TestConstants.NAME_VALID,
                    phones = listOf(
                        PhoneResponseApi(
                            ext = TestConstants.PHONE_EXT_VALID,
                            number = TestConstants.PHONE_VALID,
                            primary = TestConstants.BOOLEAN_VALID,
                            trackable = TestConstants.BOOLEAN_VALID,
                            type = TestConstants.ANY_TEXT
                        )
                    ),
                    photo = PhotoResponseApi(TestConstants.IMAGE_VALID)
                ),
                phones = listOf(
                    PhoneResponseApi(
                        ext = TestConstants.PHONE_EXT_VALID,
                        number = TestConstants.PHONE_VALID,
                        primary = TestConstants.BOOLEAN_VALID,
                        trackable = TestConstants.BOOLEAN_VALID,
                        type = TestConstants.ANY_TEXT
                    )
                ),
                webUrl = TestConstants.URL_VALID,
                recentlySoldCount = TestConstants.ANY_DIGITS,
                forSalePriceCount = TestConstants.ANY_DIGITS,
                minForSalePrice = TestConstants.PRICE_VALID,
                maxForSalePrice = TestConstants.ANY_DIGITS,
                reviewCount = TestConstants.ANY_DIGITS,
                recommendationsCount = TestConstants.ANY_DIGITS,
                salePrice = SalePriceResponseApi(
                    min = TestConstants.PRICE_VALID,
                    max = TestConstants.ANY_DIGITS,
                    lastListingDate = TestConstants.SERVER_DATE_PATTERN
                )
            ),
            AgentResponseApi(
                id = TestConstants.ID_VALID_TEXT,
                fullName = TestConstants.NAME_VALID,
                nickname = TestConstants.EMPTY_TEXT,
                name = TestConstants.NAME_VALID,
                title = TestConstants.ANY_TEXT,
                slogan = TestConstants.ANY_TEXT,
                photoUrl = PhotoResponseApi(TestConstants.PHOTO_VALID),
                backgroundPhotoUrl = TestConstants.PHOTO_VALID,
                address = AddressResponseApi(
                    city = TestConstants.LOCATION_VALID,
                    coordinate = CoordinateApi(
                        lat = TestConstants.LATITUDE_VALID,
                        lon = TestConstants.LONGITUDE_VALID
                    ),
                    line = TestConstants.ADDRESS_VALID,
                    postalCode = TestConstants.ANY_TEXT,
                    state = TestConstants.ANY_TEXT,
                    stateCode = TestConstants.ANY_TEXT
                ),
                office = OfficeResponseApi(
                    email = TestConstants.EMAIL_VALID,
                    fulfillment_id = TestConstants.ID_VALID_TEXT,
                    href = TestConstants.URL_VALID,
                    mls_set = TestConstants.STATUS_FOR_SALE,
                    name = TestConstants.NAME_VALID,
                    phones = listOf(
                        PhoneResponseApi(
                            ext = TestConstants.PHONE_EXT_VALID,
                            number = TestConstants.PHONE_VALID,
                            primary = TestConstants.BOOLEAN_VALID,
                            trackable = TestConstants.BOOLEAN_VALID,
                            type = TestConstants.ANY_TEXT
                        )
                    ),
                    photo = PhotoResponseApi(TestConstants.IMAGE_VALID)
                ),
                phones = listOf(
                    PhoneResponseApi(
                        ext = TestConstants.PHONE_EXT_VALID,
                        number = TestConstants.PHONE_VALID,
                        primary = TestConstants.BOOLEAN_VALID,
                        trackable = TestConstants.BOOLEAN_VALID,
                        type = TestConstants.ANY_TEXT
                    )
                ),
                webUrl = TestConstants.URL_VALID,
                recentlySoldCount = TestConstants.ANY_DIGITS,
                forSalePriceCount = TestConstants.ANY_DIGITS,
                minForSalePrice = TestConstants.PRICE_VALID,
                maxForSalePrice = TestConstants.ANY_DIGITS,
                reviewCount = TestConstants.ANY_DIGITS,
                recommendationsCount = TestConstants.ANY_DIGITS,
                salePrice = SalePriceResponseApi(
                    min = TestConstants.PRICE_VALID,
                    max = TestConstants.ANY_DIGITS,
                    lastListingDate = TestConstants.SERVER_DATE_PATTERN
                )
            ),
            AgentResponseApi(
                id = TestConstants.ID_VALID_TEXT,
                fullName = TestConstants.NAME_VALID,
                nickname = TestConstants.EMPTY_TEXT,
                name = TestConstants.NAME_VALID,
                title = TestConstants.ANY_TEXT,
                slogan = TestConstants.ANY_TEXT,
                photoUrl = PhotoResponseApi(TestConstants.IMAGE_VALID),
                backgroundPhotoUrl = TestConstants.PHOTO_VALID,
                address = AddressResponseApi(
                    city = TestConstants.LOCATION_VALID,
                    coordinate = CoordinateApi(
                        lat = TestConstants.LATITUDE_VALID,
                        lon = TestConstants.LONGITUDE_VALID
                    ),
                    line = TestConstants.ADDRESS_VALID,
                    postalCode = TestConstants.ANY_TEXT,
                    state = TestConstants.ANY_TEXT,
                    stateCode = TestConstants.ANY_TEXT
                ),
                office = OfficeResponseApi(
                    email = TestConstants.EMAIL_VALID,
                    fulfillment_id = TestConstants.ID_VALID_TEXT,
                    href = TestConstants.URL_VALID,
                    mls_set = TestConstants.STATUS_FOR_SALE,
                    name = TestConstants.NAME_VALID,
                    phones = listOf(
                        PhoneResponseApi(
                            ext = TestConstants.PHONE_EXT_VALID,
                            number = TestConstants.PHONE_VALID,
                            primary = TestConstants.BOOLEAN_VALID,
                            trackable = TestConstants.BOOLEAN_VALID,
                            type = TestConstants.ANY_TEXT
                        )
                    ),
                    photo = PhotoResponseApi(TestConstants.IMAGE_VALID)
                ),
                phones = listOf(
                    PhoneResponseApi(
                        ext = TestConstants.PHONE_EXT_VALID,
                        number = TestConstants.PHONE_VALID,
                        primary = TestConstants.BOOLEAN_VALID,
                        trackable = TestConstants.BOOLEAN_VALID,
                        type = TestConstants.ANY_TEXT
                    )
                ),
                webUrl = TestConstants.URL_VALID,
                recentlySoldCount = TestConstants.ANY_DIGITS,
                forSalePriceCount = TestConstants.ANY_DIGITS,
                minForSalePrice = TestConstants.PRICE_VALID,
                maxForSalePrice = TestConstants.ANY_DIGITS,
                reviewCount = TestConstants.ANY_DIGITS,
                recommendationsCount = TestConstants.ANY_DIGITS,
                salePrice = SalePriceResponseApi(
                    min = TestConstants.PRICE_VALID,
                    max = TestConstants.ANY_DIGITS,
                    lastListingDate = TestConstants.SERVER_DATE_PATTERN
                )
            ),
            AgentResponseApi(
                id = TestConstants.ID_VALID_TEXT,
                fullName = TestConstants.NAME_VALID,
                nickname = TestConstants.EMPTY_TEXT,
                name = TestConstants.NAME_VALID,
                title = TestConstants.ANY_TEXT,
                slogan = TestConstants.ANY_TEXT,
                photoUrl = PhotoResponseApi(TestConstants.IMAGE_VALID),
                backgroundPhotoUrl = TestConstants.PHOTO_VALID,
                address = AddressResponseApi(
                    city = TestConstants.LOCATION_VALID,
                    coordinate = CoordinateApi(
                        lat = TestConstants.LATITUDE_VALID,
                        lon = TestConstants.LONGITUDE_VALID
                    ),
                    line = TestConstants.ADDRESS_VALID,
                    postalCode = TestConstants.ANY_TEXT,
                    state = TestConstants.ANY_TEXT,
                    stateCode = TestConstants.ANY_TEXT
                ),
                office = OfficeResponseApi(
                    email = TestConstants.EMAIL_VALID,
                    fulfillment_id = TestConstants.ID_VALID_TEXT,
                    href = TestConstants.URL_VALID,
                    mls_set = TestConstants.STATUS_FOR_SALE,
                    name = TestConstants.NAME_VALID,
                    phones = listOf(
                        PhoneResponseApi(
                            ext = TestConstants.PHONE_EXT_VALID,
                            number = TestConstants.PHONE_VALID,
                            primary = TestConstants.BOOLEAN_VALID,
                            trackable = TestConstants.BOOLEAN_VALID,
                            type = TestConstants.ANY_TEXT
                        )
                    ),
                    photo = PhotoResponseApi(TestConstants.IMAGE_VALID)
                ),
                phones = listOf(
                    PhoneResponseApi(
                        ext = TestConstants.PHONE_EXT_VALID,
                        number = TestConstants.PHONE_VALID,
                        primary = TestConstants.BOOLEAN_VALID,
                        trackable = TestConstants.BOOLEAN_VALID,
                        type = TestConstants.ANY_TEXT
                    )
                ),
                webUrl = TestConstants.URL_VALID,
                recentlySoldCount = TestConstants.ANY_DIGITS,
                forSalePriceCount = TestConstants.ANY_DIGITS,
                minForSalePrice = TestConstants.PRICE_VALID,
                maxForSalePrice = TestConstants.ANY_DIGITS,
                reviewCount = TestConstants.ANY_DIGITS,
                recommendationsCount = TestConstants.ANY_DIGITS,
                salePrice = SalePriceResponseApi(
                    min = TestConstants.PRICE_VALID,
                    max = TestConstants.ANY_DIGITS,
                    lastListingDate = TestConstants.SERVER_DATE_PATTERN
                )
            )
        )
    )

    fun getAgent(): AgentEntity = getExpectedAgentsList().first()

    fun getAgentDetails(): BaseAgentDetailsResponse = BaseAgentDetailsResponse(
        AgentDetailsResponseApi(
            firstName = TestConstants.ANY_TEXT,
            lastName = TestConstants.ANY_TEXT,
            fullName = TestConstants.ANY_TEXT,
            description = TestConstants.ANY_TEXT,
            agentRating = TestConstants.ANY_DIGITS,
            phone = TestConstants.PHONE_VALID,
            email = TestConstants.EMAIL_VALID,
            website = TestConstants.ANY_TEXT,
            photo = PhotoResponseApi(url = TestConstants.PHOTO_VALID),
            address = AddressResponseApi(
                city = TestConstants.ANY_TEXT,
                coordinate = CoordinateApi(
                    lat = TestConstants.LATITUDE_VALID,
                    lon = TestConstants.LONGITUDE_VALID
                ),
                line = TestConstants.ANY_TEXT,
                postalCode = TestConstants.ANY_TEXT,
                state = TestConstants.ANY_TEXT,
                stateCode = TestConstants.ANY_TEXT
            ),
            broker = BrokerResponseApi(
                name = TestConstants.ANY_TEXT,
                photo = PhotoResponseApi(url = TestConstants.URL_VALID)
            ),
            specialization = listOf(TestConstants.ANY_TEXT, TestConstants.ANY_TEXT),
            language = listOf(TestConstants.ANY_TEXT, TestConstants.ANY_TEXT),
            reviewCount = TestConstants.ANY_DIGITS,
            recommendationsCount = TestConstants.ANY_DIGITS,
            averageRating = TestConstants.ANY_FLOAT,
            reviews = listOf(
                ReviewResponseApi(
                    author = TestConstants.ANY_TEXT,
                    description = TestConstants.ANY_TEXT,
                    ratingValue = TestConstants.ANY_DIGITS
                ),
                ReviewResponseApi(
                    author = TestConstants.ANY_TEXT,
                    description = TestConstants.ANY_TEXT,
                    ratingValue = TestConstants.ANY_DIGITS
                )
            ),
            marketingArea = listOf(
                AreaResponseApi(
                    name = TestConstants.ANY_TEXT,
                    stateCode = TestConstants.ANY_TEXT
                ),
                AreaResponseApi(
                    name = TestConstants.ANY_TEXT,
                    stateCode = TestConstants.ANY_TEXT
                )
            ),
            servedAreas = listOf(
                AreaResponseApi(
                    name = TestConstants.ANY_TEXT,
                    stateCode = TestConstants.ANY_TEXT
                ),
                AreaResponseApi(
                    name = TestConstants.ANY_TEXT,
                    stateCode = TestConstants.ANY_TEXT
                )
            ),
            phones = listOf(
                PhoneResponseApi(
                    ext = TestConstants.PHONE_EXT_VALID,
                    number = TestConstants.PHONE_VALID,
                    primary = true,
                    trackable = true,
                    type = TestConstants.ANY_TEXT
                ),
                PhoneResponseApi(
                    ext = null,
                    number = TestConstants.PHONE_VALID,
                    primary = false,
                    trackable = true,
                    type = TestConstants.ANY_TEXT
                )
            )
        )
    )
}