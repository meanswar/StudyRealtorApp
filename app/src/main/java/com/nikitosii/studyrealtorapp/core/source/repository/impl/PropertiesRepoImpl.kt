package com.nikitosii.studyrealtorapp.core.source.repository.impl

import com.nikitosii.studyrealtorapp.core.source.local.model.Property
import com.nikitosii.studyrealtorapp.core.source.local.model.request.SalesRequest
import com.nikitosii.studyrealtorapp.core.source.net.NetworkErrorHandler
import com.nikitosii.studyrealtorapp.core.source.net.api.PropertiesApi
import com.nikitosii.studyrealtorapp.core.source.repository.PropertiesRepo
import com.nikitosii.studyrealtorapp.core.source.repository.base.BaseRepo
import javax.inject.Inject

class PropertiesRepoImpl @Inject constructor(
    private val api: PropertiesApi,
    networkErrorHandler: NetworkErrorHandler
) : BaseRepo(networkErrorHandler), PropertiesRepo {

    override suspend fun getPropertiesForSale(
        data: SalesRequest,
        page: Int,
        sort: String?
    ): List<Property> = runWithErrorHandler { val result = api.getPropertiesForSale(
            data.address,
            data.houses?.joinToString(",") { it },
            data.priceMin,
            data.priceMax,
            data.bedsMin,
            data.bedsMax,
            data.bathsMin,
            data.bathsMax,
            data.sqftMin,
            data.sqftMax,
            page,
            sort
        ).homeSearch?.results
        result?.map { Property.from(it) } ?: listOf()
    }
}