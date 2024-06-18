package com.nikitosii.studyrealtorapp.core.domain.useCase.profile

import com.nikitosii.studyrealtorapp.core.domain.repository.ProfileRepo
import com.nikitosii.studyrealtorapp.core.domain.useCase.base.UseCaseParams
import com.nikitosii.studyrealtorapp.core.source.local.model.profile.Profile
import javax.inject.Inject

class UpdateProfileUseCase @Inject constructor(private val repo: ProfileRepo) :
    UseCaseParams<Unit, UpdateProfileUseCase.Params>() {

    class Params private constructor(val profile: Profile) {
        companion object {
            fun create(profile: Profile) = Params(profile)
        }
    }

    override suspend fun execute(data: Params) {
        repo.updateProfile(data.profile); repo.refresh()
    }
}