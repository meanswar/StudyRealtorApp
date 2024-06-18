package com.nikitosii.studyrealtorapp.core.domain.useCase.properties

import com.nikitosii.studyrealtorapp.core.domain.repository.PropertiesRepo
import com.nikitosii.studyrealtorapp.core.domain.useCase.base.UseCaseParams
import com.nikitosii.studyrealtorapp.core.source.local.model.Property
import javax.inject.Inject

class UpdatePropertyUseCase @Inject constructor(private val repo: PropertiesRepo) :
    UseCaseParams<Unit, UpdatePropertyUseCase.Params>() {

    class Params private constructor(val data: Property) {
        companion object {
            fun create(data: Property) = Params(data)
        }
    }

    override suspend fun execute(data: Params) {
        repo.updateProperty(data.data)
        repo.refreshProperties()
    }
}