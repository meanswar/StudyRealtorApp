package com.nikitosii.studyrealtorapp.flow.dashboard

import com.nikitosii.studyrealtorapp.core.domain.useCase.profile.GetProfileFlowUseCase
import com.nikitosii.studyrealtorapp.core.domain.useCase.request.GetRecentRentSearchRequestsUseCase
import com.nikitosii.studyrealtorapp.core.domain.useCase.request.GetRecentSaleSearchRequestsUseCase
import com.nikitosii.studyrealtorapp.core.domain.useCase.request.UpdateRequestUseCase
import com.nikitosii.studyrealtorapp.core.source.local.model.request.SearchRequest
import com.nikitosii.studyrealtorapp.di.modules.AppModule
import com.nikitosii.studyrealtorapp.flow.base.BaseViewModel
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject
import javax.inject.Named

class DashboardViewModel @Inject constructor(
    getRecentSaleSearchRequestsUseCase: GetRecentSaleSearchRequestsUseCase,
    getRecentRentSearchRequestsUseCase: GetRecentRentSearchRequestsUseCase,
    getProfileFlowUseCase: GetProfileFlowUseCase,
    private val updateSearchRequestUseCase: UpdateRequestUseCase,
    @Named(AppModule.IO_DISPATCHER) ioDispatcher: CoroutineDispatcher,
    @Named(AppModule.MAIN_DISPATCHER) uiDispatcher: CoroutineDispatcher
) : BaseViewModel(ioDispatcher, uiDispatcher) {

    val recentSaleRequests = getRecentSaleSearchRequestsUseCase.execute().toWorkLiveData()
    val recentRentRequests = getRecentRentSearchRequestsUseCase.execute().toWorkLiveData()
    val profile = getProfileFlowUseCase.execute().toWorkLiveData()

    fun updateRequest(request: SearchRequest) {
        val params = UpdateRequestUseCase.Params.create(request)
        ioToUnit(io = { updateSearchRequestUseCase.execute(params) })
    }
}