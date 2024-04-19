package com.nikitosii.studyrealtorapp.core.source.useCase.properties.sale

import com.nikitosii.studyrealtorapp.core.source.local.model.request.SalesRequest
import com.nikitosii.studyrealtorapp.core.source.repository.SalePropertiesRepo
import com.nikitosii.studyrealtorapp.core.source.useCase.base.FlowUseCase
import com.nikitosii.studyrealtorapp.util.Flow
import javax.inject.Inject

class GetRecentSaleRequestsUseCase @Inject constructor(private val repo: SalePropertiesRepo) :
    FlowUseCase<List<SalesRequest>>() {

    override fun execute(): Flow<List<SalesRequest>> = repo.getLastSaleRequestsHistory()
}