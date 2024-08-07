package com.nikitosii.studyrealtorapp.core.domain.useCase.profile

import com.nikitosii.studyrealtorapp.core.domain.repository.ProfileRepo
import com.nikitosii.studyrealtorapp.core.domain.useCase.base.FlowUseCase
import com.nikitosii.studyrealtorapp.core.source.local.model.profile.Profile
import com.nikitosii.studyrealtorapp.util.Flow
import javax.inject.Inject

class GetProfileFlowUseCase @Inject constructor(private val repo: ProfileRepo) :
    FlowUseCase<Profile>() {

    override fun execute(): Flow<Profile> = repo.getProfile()
}