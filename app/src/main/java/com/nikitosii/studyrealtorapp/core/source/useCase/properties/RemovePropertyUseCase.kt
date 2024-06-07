package com.nikitosii.studyrealtorapp.core.source.useCase.properties

import com.nikitosii.studyrealtorapp.core.source.repository.PropertiesRepo
import com.nikitosii.studyrealtorapp.core.source.useCase.base.UseCaseParams
import javax.inject.Inject

class RemovePropertyUseCase @Inject constructor(private val repo: PropertiesRepo) :
    UseCaseParams<Unit, RemovePropertyUseCase.Params>() {
    class Params private constructor(val id: String) {
        companion object {
            fun create(id: String) = Params(id)
        }
    }

    override suspend fun execute(data: Params) {
        repo.remove(data.id)
        repo.refreshProperties()
    }
}