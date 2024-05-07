package com.nikitosii.studyrealtorapp.core.source.useCase.properties

import com.nikitosii.studyrealtorapp.core.source.local.model.request.RequestType
import com.nikitosii.studyrealtorapp.core.source.local.model.request.SearchRequest
import com.nikitosii.studyrealtorapp.core.source.repository.SearchRequestRepo
import com.nikitosii.studyrealtorapp.core.source.useCase.base.UseCaseParams
import javax.inject.Inject

class GetRecentSearchRequestsUseCase @Inject constructor(
    private val repo: SearchRequestRepo
) : UseCaseParams<List<SearchRequest>, GetRecentSearchRequestsUseCase.Params>() {

    class Params private constructor(val type: RequestType) {
        companion object {
            fun create(type: RequestType) = Params(type)
        }
    }

    override suspend fun execute(data: Params): List<SearchRequest> =
        repo.getRecentSearchRequests(data.type)
}