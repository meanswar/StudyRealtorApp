package com.nikitosii.studyrealtorapp.core.source.useCase.properties.sale

import com.nikitosii.studyrealtorapp.core.source.local.model.request.SalesRequest
import com.nikitosii.studyrealtorapp.core.source.repository.PropertiesRepo
import com.nikitosii.studyrealtorapp.core.source.useCase.base.UseCase
import javax.inject.Inject

class GetSaleRequestsUseCase @Inject constructor(private val repo: PropertiesRepo) :
    UseCase<List<SalesRequest>>() {

    override suspend fun execute(): List<SalesRequest> = repo.getSaleRequestsHistory()
}