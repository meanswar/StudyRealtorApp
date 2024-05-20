package com.nikitosii.studyrealtorapp.core.source.useCase.properties.sale

import com.nikitosii.studyrealtorapp.core.source.local.model.request.RequestType
import com.nikitosii.studyrealtorapp.core.source.local.model.request.SearchRequest
import com.nikitosii.studyrealtorapp.core.source.repository.SearchRequestRepo
import com.nikitosii.studyrealtorapp.core.source.useCase.base.FlowUseCase
import com.nikitosii.studyrealtorapp.util.Flow
import javax.inject.Inject

class GetRecentSaleSearchRequestsUseCase @Inject constructor(
    private val repo: SearchRequestRepo
) : FlowUseCase<List<SearchRequest>>() {

    override fun execute(): Flow<List<SearchRequest>> = repo.getRecentSearchRequests(RequestType.SALE)
}