package com.nikitosii.studyrealtorapp.core.domain.useCase.properties

import com.nikitosii.studyrealtorapp.core.domain.repository.PropertiesRepo
import com.nikitosii.studyrealtorapp.core.domain.useCase.base.UseCaseParams
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