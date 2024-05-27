package com.nikitosii.studyrealtorapp.flow.profile

import com.nikitosii.studyrealtorapp.core.source.useCase.profile.GetProfileFlowUseCase
import com.nikitosii.studyrealtorapp.flow.base.BaseViewModel
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    getProfileFlowUseCase: GetProfileFlowUseCase
): BaseViewModel() {

    val profile = getProfileFlowUseCase.execute().toWorkLiveData()
}