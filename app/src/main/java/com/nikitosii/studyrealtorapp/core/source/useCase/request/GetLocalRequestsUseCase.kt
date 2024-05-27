package com.nikitosii.studyrealtorapp.core.source.useCase.request

import com.nikitosii.studyrealtorapp.core.source.local.model.request.SearchRequest
import com.nikitosii.studyrealtorapp.core.source.repository.SearchRequestRepo
import com.nikitosii.studyrealtorapp.core.source.useCase.base.UseCase
import javax.inject.Inject

class GetLocalRequestsUseCase @Inject constructor(private val repo: SearchRequestRepo): UseCase<List<SearchRequest>>() {

    override suspend fun execute(): List<SearchRequest> = repo.getLocalRequests()
}