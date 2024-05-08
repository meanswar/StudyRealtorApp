package com.nikitosii.studyrealtorapp.core.source.repository.impl

import com.nikitosii.studyrealtorapp.core.source.db.dao.SearchRequestDao
import com.nikitosii.studyrealtorapp.core.source.db.entity.SearchRequestEntity
import com.nikitosii.studyrealtorapp.core.source.local.model.request.RequestType
import com.nikitosii.studyrealtorapp.core.source.local.model.request.SearchRequest
import com.nikitosii.studyrealtorapp.core.source.repository.SearchRequestRepo
import javax.inject.Inject

class SearchRequestRepoImpl @Inject constructor(
    private val dao: SearchRequestDao,
) : SearchRequestRepo {


    override suspend fun saveSearchRequest(request: SearchRequest): SearchRequest {
        val id = dao.insert(SearchRequest.toEntity(request))
        return request.copy(id = id.toInt())
    }

    override suspend fun removeSearchRequest(id: Int) { dao.remove(id) }

    override suspend fun getRecentSearchRequests(type: RequestType): List<SearchRequest> =
        dao.getRecentSearchRequests(type).map { SearchRequest.from(it) }

    override suspend fun updateSearchRequest(request: SearchRequest) {
        val entity = SearchRequest.toEntity(request)
        dao.insert(entity)
    }
}