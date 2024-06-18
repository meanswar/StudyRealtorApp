package com.nikitosii.studyrealtorapp.core.domain.useCase.request

import com.nikitosii.studyrealtorapp.core.domain.repository.RequestDataRepo
import com.nikitosii.studyrealtorapp.core.domain.repository.SearchRequestRepo
import com.nikitosii.studyrealtorapp.core.domain.useCase.base.UseCaseParams
import javax.inject.Inject

class RemoveSearchRequestUseCase @Inject constructor(
    private val requestRepo: SearchRequestRepo,
    private val searchDataRepo: RequestDataRepo
) : UseCaseParams<Unit, RemoveSearchRequestUseCase.Params>() {
    class Params private constructor(val id: Int) {
        companion object {
            fun create(id: Int): Params = Params(id)
        }
    }

    override suspend fun execute(data: Params) {
        searchDataRepo.delete(data.id)
        requestRepo.removeSearchRequest(data.id)
        requestRepo.refreshSearchRequests()
    }
}