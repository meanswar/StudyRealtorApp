package com.nikitosii.studyrealtorapp.di.api

import com.nikitosii.studyrealtorapp.TestConstants.ANY_DIGITS
import com.nikitosii.studyrealtorapp.TestConstants.ANY_FLOAT
import com.nikitosii.studyrealtorapp.TestConstants.ANY_TEXT
import com.nikitosii.studyrealtorapp.TestConstants.EMAIL_VALID
import com.nikitosii.studyrealtorapp.TestConstants.ID_VALID_TEXT
import com.nikitosii.studyrealtorapp.TestConstants.LATITUDE_VALID
import com.nikitosii.studyrealtorapp.TestConstants.LOCATION_VALID
import com.nikitosii.studyrealtorapp.TestConstants.LONGITUDE_VALID
import com.nikitosii.studyrealtorapp.TestConstants.PAGE_VALID
import com.nikitosii.studyrealtorapp.TestConstants.PHONE_EXT_VALID
import com.nikitosii.studyrealtorapp.TestConstants.PHONE_START_WITH_PLUS_VALID
import com.nikitosii.studyrealtorapp.TestConstants.PHONE_VALID
import com.nikitosii.studyrealtorapp.TestConstants.PHOTO_VALID
import com.nikitosii.studyrealtorapp.TestConstants.REQUEST_DIGITS_VALID
import com.nikitosii.studyrealtorapp.TestConstants.REQUEST_TEXT_VALID
import com.nikitosii.studyrealtorapp.TestConstants.SERVER_DATE_PATTERN
import com.nikitosii.studyrealtorapp.TestConstants.URL_VALID
import com.nikitosii.studyrealtorapp.core.source.net.api.AgentsApi
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
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object AgentsApiTest {
    @Provides
    @Singleton
    internal fun providesAgentsApi(): AgentsApi = object : AgentsApi {
        override suspend fun getAgents(
            address: String,
            name: String?,
            rating: Int?,
            isPhotoSet: Boolean?,
            language: String?,
            price: String?,
            page: Int
        ): BaseAgentsSearch {
            return when {

                address == LOCATION_VALID &&
                        (price == null || price == REQUEST_TEXT_VALID) &&
                        (name == REQUEST_TEXT_VALID || name == null) &&
                        (rating == REQUEST_DIGITS_VALID || rating == null) &&
                        (language == REQUEST_TEXT_VALID || language == null) &&
                        page == PAGE_VALID -> BaseAgentsSearch(
                    agents = listOf(
                        AgentResponseApi(
                            fullName = ANY_TEXT,
                            nickname = ANY_TEXT,
                            name = ANY_TEXT,
                            title = ANY_TEXT,
                            slogan = ANY_TEXT,
                            photoUrl = PhotoResponseApi(url = PHOTO_VALID),
                            backgroundPhotoUrl = PHOTO_VALID,
                            address = AddressResponseApi(
                                city = ANY_TEXT,
                                coordinate = CoordinateApi(
                                    lat = LATITUDE_VALID,
                                    lon = LONGITUDE_VALID
                                ),
                                line = ANY_TEXT,
                                postalCode = ANY_TEXT,
                                state = ANY_TEXT,
                                stateCode = ANY_TEXT
                            ),
                            office = OfficeResponseApi(
                                email = EMAIL_VALID,
                                fulfillment_id = ID_VALID_TEXT,
                                href = ANY_TEXT,
                                mls_set = ANY_TEXT,
                                name = ANY_TEXT,
                                phones = listOf(
                                    PhoneResponseApi(
                                        ext = PHONE_EXT_VALID,
                                        number = PHONE_VALID,
                                        primary = true,
                                        trackable = true,
                                        type = ANY_TEXT
                                    )
                                ),
                                photo = PhotoResponseApi(url = PHOTO_VALID)
                            ),
                            phones = listOf(
                                PhoneResponseApi(
                                    ext = PHONE_EXT_VALID,
                                    number = PHONE_VALID,
                                    primary = true,
                                    trackable = true,
                                    type = ANY_TEXT
                                ),
                                PhoneResponseApi(
                                    ext = null,
                                    number = PHONE_START_WITH_PLUS_VALID,
                                    primary = false,
                                    trackable = true,
                                    type = ANY_TEXT
                                )
                            ),
                            webUrl = ANY_TEXT,
                            recentlySoldCount = ANY_DIGITS,
                            forSalePriceCount = ANY_DIGITS,
                            minForSalePrice = ANY_DIGITS,
                            maxForSalePrice = ANY_DIGITS,
                            reviewCount = ANY_DIGITS,
                            recommendationsCount = ANY_DIGITS,
                            id = ID_VALID_TEXT,
                            salePrice = SalePriceResponseApi(
                                min = ANY_DIGITS,
                                max = ANY_DIGITS,
                                lastListingDate = SERVER_DATE_PATTERN
                            )
                        )
                    )
                )
                else -> throw Exception("wrong params")
            }
        }

        override suspend fun getAgentDetails(id: String): BaseAgentDetailsResponse {
            if (id != ID_VALID_TEXT) throw Exception("wrong agent id")
            return BaseAgentDetailsResponse(
                AgentDetailsResponseApi(
                    firstName = ANY_TEXT,
                    lastName = ANY_TEXT,
                    fullName = ANY_TEXT,
                    description = ANY_TEXT,
                    agentRating = ANY_DIGITS,
                    phone = PHONE_VALID,
                    email = EMAIL_VALID,
                    website = ANY_TEXT,
                    photo = PhotoResponseApi(url = PHOTO_VALID),
                    address = AddressResponseApi(
                        city = ANY_TEXT,
                        coordinate = CoordinateApi(lat = LATITUDE_VALID, lon = LONGITUDE_VALID),
                        line = ANY_TEXT,
                        postalCode = ANY_TEXT,
                        state = ANY_TEXT,
                        stateCode = ANY_TEXT
                    ),
                    broker = BrokerResponseApi(
                        name = ANY_TEXT,
                        photo = PhotoResponseApi(url = URL_VALID)
                    ),
                    specialization = listOf(ANY_TEXT, ANY_TEXT),
                    language = listOf(ANY_TEXT, ANY_TEXT),
                    reviewCount = ANY_DIGITS,
                    recommendationsCount = ANY_DIGITS,
                    averageRating = ANY_FLOAT,
                    reviews = listOf(
                        ReviewResponseApi(
                            author = ANY_TEXT,
                            description = ANY_TEXT,
                            ratingValue = ANY_DIGITS
                        ),
                        ReviewResponseApi(
                            author = ANY_TEXT,
                            description = ANY_TEXT,
                            ratingValue = ANY_DIGITS
                        )
                    ),
                    marketingArea = listOf(
                        AreaResponseApi(
                            name = ANY_TEXT,
                            stateCode = ANY_TEXT
                        ),
                        AreaResponseApi(
                            name = ANY_TEXT,
                            stateCode = ANY_TEXT
                        )
                    ),
                    servedAreas = listOf(
                        AreaResponseApi(
                            name = ANY_TEXT,
                            stateCode = ANY_TEXT
                        ),
                        AreaResponseApi(
                            name = ANY_TEXT,
                            stateCode = ANY_TEXT
                        )
                    ),
                    phones = listOf(
                        PhoneResponseApi(
                            ext = PHONE_EXT_VALID,
                            number = PHONE_VALID,
                            primary = true,
                            trackable = true,
                            type = ANY_TEXT
                        ),
                        PhoneResponseApi(
                            ext = null,
                            number = PHONE_START_WITH_PLUS_VALID,
                            primary = false,
                            trackable = true,
                            type = ANY_TEXT
                        )
                    )
                )
            )
        }
    }
}