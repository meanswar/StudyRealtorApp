package com.nikitosii.studyrealtorapp.di.db.dao

import com.nikitosii.studyrealtorapp.core.source.db.dao.SearchRequestDao
import com.nikitosii.studyrealtorapp.core.source.db.entity.SearchRequestEntity
import com.nikitosii.studyrealtorapp.core.source.local.model.request.RequestType

object SearchRequestDaoTest : SearchRequestDao {

    private val searchRequestList = mutableListOf<SearchRequestEntity>()

    override fun getSearchRequest(requestType: RequestType): List<SearchRequestEntity> =
        searchRequestList.filter { it.requestType == requestType }

    override fun getAllSearchRequests(): List<SearchRequestEntity> = searchRequestList

    override fun getRecentSearchRequests(requestType: RequestType): List<SearchRequestEntity> =
        searchRequestList.filter { it.requestType == requestType }.takeLast(3)

    override fun insert(entity: SearchRequestEntity): Long {
        searchRequestList.add(entity)
        return searchRequestList.indexOf(entity).toLong()
    }

    override fun removeAll() {
        searchRequestList.removeAll { true }
    }

    override fun remove(id: Int) {
        searchRequestList.removeIf { it.id == id }
    }

    override fun removeNot(id: Int) {
        searchRequestList.removeIf { it.id != id }
    }
}