package com.nikitosii.studyrealtorapp.di.db.dao

import com.nikitosii.studyrealtorapp.core.source.db.dao.SearchPropertiesDao
import com.nikitosii.studyrealtorapp.core.source.db.entity.SearchPropertiesDataEntity
import com.nikitosii.studyrealtorapp.core.source.local.model.request.RequestType
import com.nikitosii.studyrealtorapp.core.source.local.model.request.SearchRequest

object SearchPropertiesDaoTest: SearchPropertiesDao {

    private val searchPropertiesList = mutableListOf<SearchPropertiesDataEntity>()
    override fun insert(entity: SearchPropertiesDataEntity) {
        searchPropertiesList.add(entity)
    }

    override fun insertAll(entities: List<SearchPropertiesDataEntity>) {
        searchPropertiesList.addAll(entities)
    }

    override fun getAll(): List<SearchPropertiesDataEntity> = searchPropertiesList

    override fun getById(id: Int): SearchPropertiesDataEntity {
        return searchPropertiesList.first { it.id == id }
    }

    override fun getByQuery(query: SearchRequest): SearchPropertiesDataEntity {
        return searchPropertiesList.first { it.query == query }
    }

    override fun getLastSaleRequests(): List<SearchRequest> {
        val saleRequests = searchPropertiesList.filter { it.query.requestType == RequestType.SALE }
        return saleRequests.takeLast(3).map { it.query }
    }

    override fun getSearchRequests(): List<SearchRequest> = searchPropertiesList.map { it.query }

    override fun deleteById(id: Int) {
        searchPropertiesList.removeIf { it.id == id }
    }

    override fun deleteAll() {
        searchPropertiesList.removeAll { true }
    }
}