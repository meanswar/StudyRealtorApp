package com.nikitosii.studyrealtorapp.core.source.useCase.profile

import com.nikitosii.studyrealtorapp.core.source.repository.ProfileRepo
import com.nikitosii.studyrealtorapp.core.source.useCase.base.UseCase
import javax.inject.Inject

class RemoveProfileDataUseCase @Inject constructor(private val repo: ProfileRepo): UseCase<Unit>() {

    override suspend fun execute() = run {  repo.removeProfileData() }
}