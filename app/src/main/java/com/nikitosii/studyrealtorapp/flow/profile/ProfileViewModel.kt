package com.nikitosii.studyrealtorapp.flow.profile

import com.nikitosii.studyrealtorapp.core.source.useCase.profile.GetProfileFlowUseCase
import com.nikitosii.studyrealtorapp.core.source.useCase.profile.RemoveDataUseCase
import com.nikitosii.studyrealtorapp.core.source.useCase.profile.RemoveProfileDataUseCase
import com.nikitosii.studyrealtorapp.flow.base.BaseViewModel
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    getProfileFlowUseCase: GetProfileFlowUseCase,
    private val removeProfileDataUseCase: RemoveProfileDataUseCase,
    private val removeDataUseCase: RemoveDataUseCase
): BaseViewModel() {

    val profile = getProfileFlowUseCase.execute().toWorkLiveData()

    fun removeProfileData() = ioToUnit { removeProfileDataUseCase.execute() }

    fun removeData() = ioToUnit { removeDataUseCase.execute() }
}