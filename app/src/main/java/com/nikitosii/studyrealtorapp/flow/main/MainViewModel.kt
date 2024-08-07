package com.nikitosii.studyrealtorapp.flow.main

import com.nikitosii.studyrealtorapp.core.domain.useCase.agent.GetLocalAgentsUseCase
import com.nikitosii.studyrealtorapp.core.domain.useCase.agent.GetRecentFavoriteAgentsUseCase
import com.nikitosii.studyrealtorapp.core.domain.useCase.profile.RemoveProfileDataUseCase
import com.nikitosii.studyrealtorapp.core.domain.useCase.properties.GetAllLocalPropertiesUseCase
import com.nikitosii.studyrealtorapp.core.domain.useCase.request.GetLocalRequestsUseCase
import com.nikitosii.studyrealtorapp.flow.base.BaseViewModel
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val removeProfileDataUseCase: RemoveProfileDataUseCase,
    getRecentFavoriteAgentsUseCase: GetRecentFavoriteAgentsUseCase,
    getLocalPropertiesUseCase: GetAllLocalPropertiesUseCase,
    getLocalRequestsUseCase: GetLocalRequestsUseCase,
    getLocalAgentsUseCase: GetLocalAgentsUseCase
) : BaseViewModel() {

    private val recentFavoriteAgents = getRecentFavoriteAgentsUseCase.execute()
    private val properties = getLocalPropertiesUseCase.execute()
    private val requests = getLocalRequestsUseCase.execute()
    private val agents = getLocalAgentsUseCase.execute()
    fun removeProfileData() = ioToUnit { removeProfileDataUseCase.execute() }
}