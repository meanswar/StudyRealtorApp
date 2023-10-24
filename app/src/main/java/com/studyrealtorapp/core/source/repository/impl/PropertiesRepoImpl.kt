package com.studyrealtorapp.core.source.repository.impl

import com.studyrealtorapp.core.source.net.NetworkErrorHandler
import com.studyrealtorapp.core.source.net.api.PropertiesApi
import com.studyrealtorapp.core.source.net.model.property.PropertyResponseApi
import com.studyrealtorapp.core.source.repository.PropertiesRepo
import com.studyrealtorapp.core.source.repository.base.BaseRepo

class PropertiesRepoImpl(
    private val api: PropertiesApi,
    networkErrorHandler: NetworkErrorHandler
): BaseRepo(networkErrorHandler), PropertiesRepo {

    override suspend fun getPropertiesForSale(data: String): List<PropertyResponseApi> = runWithErrorHandler {
        api.getPropertiesForSale(data).results
    }
}