package com.nikitosii.studyrealtorapp.core.domain.useCase.request

import com.nikitosii.studyrealtorapp.core.domain.repository.SearchRequestRepo
import com.nikitosii.studyrealtorapp.core.domain.useCase.base.UseCaseParams
import com.nikitosii.studyrealtorapp.core.source.local.model.request.RequestType
import com.nikitosii.studyrealtorapp.core.source.local.model.request.SearchRequest
import javax.inject.Inject

class UpdateRequestUseCase @Inject constructor(private val repo: SearchRequestRepo) :
    UseCaseParams<Unit, UpdateRequestUseCase.Params>() {

    class Params(val data: SearchRequest) {
        companion object {
            fun create(data: SearchRequest) = Params(data)
        }
    }

    override suspend fun execute(data: Params) {
        repo.updateSearchRequest(data.data)
        repo.refreshSearchRequests()
        repo.refreshRecentSearchRequests(RequestType.RENT)
        repo.refreshRecentSearchRequests(RequestType.SALE)
    }
}