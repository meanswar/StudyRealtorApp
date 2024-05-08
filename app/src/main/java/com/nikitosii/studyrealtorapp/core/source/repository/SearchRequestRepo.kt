package com.nikitosii.studyrealtorapp.core.source.repository

import com.nikitosii.studyrealtorapp.core.source.local.model.request.RequestType
import com.nikitosii.studyrealtorapp.core.source.local.model.request.SearchRequest

interface SearchRequestRepo {

    /**
     * Saves a search request into the local storage.
     *
     * @param request The [SearchRequest] model to be saved.
     * @return The [SearchRequest] model with the ID of the newly inserted search request.
     */

    suspend fun saveSearchRequest(request: SearchRequest): SearchRequest

    /**
     * Removes a search request from the local storage.
     *
     * @param id The ID of the search request to be removed.
     */

    suspend fun removeSearchRequest(id: Int)

    /**
     * Retrieves the last three recent search requests for buying or renting properties from the local storage.
     *
     * @param type The type of the search request ([RequestType.SALE] or [RequestType.RENT]).
     * @return A list containing the last three recent search requests of the specified type.
     */

    suspend fun getRecentSearchRequests(type: RequestType): List<SearchRequest>

    suspend fun updateSearchRequest(request: SearchRequest)
}