package com.nikitosii.studyrealtorapp.flow.main

import com.nikitosii.studyrealtorapp.core.source.useCase.profile.RemoveProfileDataUseCase
import com.nikitosii.studyrealtorapp.flow.base.BaseViewModel
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val removeProfileDataUseCase: RemoveProfileDataUseCase
) : BaseViewModel() {
    fun removeProfileData() = ioToUnit { removeProfileDataUseCase.execute() }
}