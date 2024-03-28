package com.nikitosii.studyrealtorapp.core.source.repository.impl

import com.nikitosii.studyrealtorapp.core.source.local.model.Property
import com.nikitosii.studyrealtorapp.core.source.net.NetworkErrorHandler
import com.nikitosii.studyrealtorapp.core.source.net.api.PropertiesApi
import com.nikitosii.studyrealtorapp.core.source.repository.PropertiesRepo
import com.nikitosii.studyrealtorapp.core.source.repository.base.BaseRepo
import javax.inject.Inject

class PropertiesRepoImpl @Inject constructor(
    private val api: PropertiesApi,
    networkErrorHandler: NetworkErrorHandler
): BaseRepo(networkErrorHandler), PropertiesRepo {

    override suspend fun getPropertiesForSale(data: String): List<Property> = runWithErrorHandler {
        val result = api.getPropertiesForSale(data).results
        result.map { Property.from(it) }
    }
}