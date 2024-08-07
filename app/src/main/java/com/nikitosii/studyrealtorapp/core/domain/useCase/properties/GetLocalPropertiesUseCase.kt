package com.nikitosii.studyrealtorapp.core.domain.useCase.properties

import com.nikitosii.studyrealtorapp.core.domain.repository.PropertiesRepo
import com.nikitosii.studyrealtorapp.core.domain.repository.RequestDataRepo
import com.nikitosii.studyrealtorapp.core.domain.useCase.base.UseCaseParams
import com.nikitosii.studyrealtorapp.core.source.local.model.Property
import javax.inject.Inject

class GetLocalPropertiesUseCase @Inject constructor(
    private val propertiesRepo: PropertiesRepo,
    private val requestDataRepo: RequestDataRepo
) : UseCaseParams<List<Property>, GetLocalPropertiesUseCase.Params>() {

    class Params private constructor(val requestId: Int) {
        companion object {
            fun from(requestId: Int): Params = Params(requestId)
        }
    }

    override suspend fun execute(data: Params): List<Property> {
        val requestData = requestDataRepo.getData(data.requestId)
        return propertiesRepo.getLocalProperties(requestData.propertiesIds)
    }
}