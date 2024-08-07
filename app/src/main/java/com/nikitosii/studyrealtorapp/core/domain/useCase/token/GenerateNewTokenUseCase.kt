package com.nikitosii.studyrealtorapp.core.domain.useCase.token

import com.nikitosii.studyrealtorapp.core.domain.repository.TokenRepo
import com.nikitosii.studyrealtorapp.core.domain.useCase.base.UseCase
import javax.inject.Inject

class GenerateNewTokenUseCase @Inject constructor(private val repo: TokenRepo) : UseCase<Unit>() {
    override suspend fun execute() = repo.generateNewToken()
}