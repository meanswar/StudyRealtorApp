package com.nikitosii.studyrealtorapp.di.api

import com.nikitosii.studyrealtorapp.core.source.net.api.AgentsApi
import com.nikitosii.studyrealtorapp.core.source.net.model.base.BaseAgentDetailsResponse
import com.nikitosii.studyrealtorapp.core.source.net.model.base.BaseAgentsSearch
import com.nikitosii.studyrealtorapp.util.AgentTestUtils
import com.nikitosii.studyrealtorapp.util.TestConstants.EXCEPTION_WRONG_PARAMS
import com.nikitosii.studyrealtorapp.util.TestConstants.ID_VALID_TEXT
import com.nikitosii.studyrealtorapp.util.TestConstants.LOCATION_VALID
import com.nikitosii.studyrealtorapp.util.TestConstants.PAGE_VALID
import com.nikitosii.studyrealtorapp.util.TestConstants.REQUEST_DIGITS_VALID
import com.nikitosii.studyrealtorapp.util.TestConstants.REQUEST_TEXT_VALID
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
                        page == PAGE_VALID -> AgentTestUtils.getAgentsFromNetwork()

                else -> throw Exception("wrong params")
            }
        }

        override suspend fun getAgentDetails(id: String): BaseAgentDetailsResponse {
            if (id != ID_VALID_TEXT) throw Exception(EXCEPTION_WRONG_PARAMS)
            return AgentTestUtils.getAgentDetails()
        }
    }
}