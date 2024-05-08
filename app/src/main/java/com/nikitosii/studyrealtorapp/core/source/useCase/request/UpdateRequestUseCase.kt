package com.nikitosii.studyrealtorapp.core.source.useCase.request

import com.nikitosii.studyrealtorapp.core.source.local.model.request.SearchRequest
import com.nikitosii.studyrealtorapp.core.source.repository.SearchRequestRepo
import com.nikitosii.studyrealtorapp.core.source.useCase.base.UseCaseParams
import javax.inject.Inject

class UpdateRequestUseCase @Inject constructor(private val repo: SearchRequestRepo) :
    UseCaseParams<Unit, UpdateRequestUseCase.Params>() {

    class Params(val data: SearchRequest) {
        companion object {
            fun create(data: SearchRequest) = Params(data)
        }
    }

    override suspend fun execute(data: Params) = repo.updateSearchRequest(data.data)
}