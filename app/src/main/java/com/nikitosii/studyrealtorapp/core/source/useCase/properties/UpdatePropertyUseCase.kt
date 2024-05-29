package com.nikitosii.studyrealtorapp.core.source.useCase.properties

import com.nikitosii.studyrealtorapp.core.source.local.model.Property
import com.nikitosii.studyrealtorapp.core.source.repository.PropertiesRepo
import com.nikitosii.studyrealtorapp.core.source.useCase.base.UseCaseParams
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