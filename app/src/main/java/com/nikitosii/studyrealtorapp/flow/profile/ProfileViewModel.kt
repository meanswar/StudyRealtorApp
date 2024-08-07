package com.nikitosii.studyrealtorapp.flow.profile

import com.nikitosii.studyrealtorapp.core.domain.useCase.profile.GetProfileFlowUseCase
import com.nikitosii.studyrealtorapp.core.domain.useCase.profile.RemoveDataUseCase
import com.nikitosii.studyrealtorapp.core.domain.useCase.profile.RemoveProfileDataUseCase
import com.nikitosii.studyrealtorapp.di.modules.AppModule
import com.nikitosii.studyrealtorapp.flow.base.BaseViewModel
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject
import javax.inject.Named

class ProfileViewModel @Inject constructor(
    getProfileFlowUseCase: GetProfileFlowUseCase,
    private val removeProfileDataUseCase: RemoveProfileDataUseCase,
    private val removeDataUseCase: RemoveDataUseCase,
    @Named(AppModule.IO_DISPATCHER) ioDispatcher: CoroutineDispatcher,
    @Named(AppModule.MAIN_DISPATCHER) uiDispatcher: CoroutineDispatcher
) : BaseViewModel(ioDispatcher, uiDispatcher) {

    val profile = getProfileFlowUseCase.execute().toWorkLiveData()

    fun removeProfileData() = ioToUnit { removeProfileDataUseCase.execute() }

    fun removeData() = ioToUnit { removeDataUseCase.execute() }
}