package com.nikitosii.studyrealtorapp.core.source.useCase.properties.sale

import com.nikitosii.studyrealtorapp.core.source.local.model.request.PropertyRequest
import com.nikitosii.studyrealtorapp.core.source.repository.PropertiesRepo
import com.nikitosii.studyrealtorapp.core.source.useCase.base.FlowUseCase
import com.nikitosii.studyrealtorapp.util.Flow
import javax.inject.Inject

class GetRecentSaleRequestsUseCase @Inject constructor(private val repo: PropertiesRepo) :
    FlowUseCase<List<PropertyRequest>>() {

    override fun execute(): Flow<List<PropertyRequest>> = repo.getLastSaleRequestsHistory()
}