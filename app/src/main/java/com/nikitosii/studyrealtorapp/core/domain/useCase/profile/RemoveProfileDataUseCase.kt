package com.nikitosii.studyrealtorapp.core.domain.useCase.profile

import com.nikitosii.studyrealtorapp.core.domain.repository.ProfileRepo
import com.nikitosii.studyrealtorapp.core.domain.useCase.base.UseCase
import javax.inject.Inject

class RemoveProfileDataUseCase @Inject constructor(private val repo: ProfileRepo) :
    UseCase<Unit>() {

    override suspend fun execute() = run { repo.removeProfileData(); repo.refresh() }
}