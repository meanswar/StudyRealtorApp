package com.nikitosii.studyrealtorapp.core.source.net.api

import com.nikitosii.studyrealtorapp.core.source.net.model.base.BaseAgentDetailsResponse
import com.nikitosii.studyrealtorapp.core.source.net.model.base.BaseAgentsSearch
import retrofit2.http.GET
import retrofit2.http.Query

interface AgentsApi {

    @GET("/search_agents")
    suspend fun getAgents(
        @Query("location") address: String,
        @Query("agentName") name: String? = null,
        @Query("rating") rating: Int? = null,
        @Query("photo") isPhotoSet: Boolean? = null,
        @Query("lang") language: String? = null,
        @Query("price") price: String?,
        @Query("page") page: Int = 1,
    ): BaseAgentsSearch

    @GET("agent")
    suspend fun getAgentDetails(@Query("id") id: String): BaseAgentDetailsResponse
}