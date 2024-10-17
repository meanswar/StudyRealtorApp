package com.nikitosii.studyrealtorapp.flow.profile.requests

import androidx.lifecycle.MutableLiveData
import com.nikitosii.studyrealtorapp.core.domain.useCase.request.GetLocalRequestsUseCase
import com.nikitosii.studyrealtorapp.core.domain.useCase.request.RemoveSearchRequestUseCase
import com.nikitosii.studyrealtorapp.core.domain.useCase.request.UpdateRequestUseCase
import com.nikitosii.studyrealtorapp.core.source.local.model.request.SearchRequest
import com.nikitosii.studyrealtorapp.di.modules.AppModule
import com.nikitosii.studyrealtorapp.flow.base.BaseViewModel
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject
import javax.inject.Named


class ProfileRequestsViewModel @Inject constructor(
    getLocalRequestsUseCase: GetLocalRequestsUseCase,
    private val updateRequestUseCase: UpdateRequestUseCase,
    private val removeSearchRequestUseCase: RemoveSearchRequestUseCase,
    @Named(AppModule.IO_DISPATCHER) ioDispatcher: CoroutineDispatcher,
    @Named(AppModule.MAIN_DISPATCHER) uiDispatcher: CoroutineDispatcher
) : BaseViewModel(ioDispatcher, uiDispatcher) {

    val requestsNetwork = getLocalRequestsUseCase.execute().toWorkLiveData()
    val requests = MutableLiveData<List<SearchRequest>>()

    fun updateRequest(data: SearchRequest) {
        val params = UpdateRequestUseCase.Params.create(data.copy(favorite = !data.favorite))
        ioToUnit { updateRequestUseCase.execute(params) }
    }

    fun removeRequest(id: Int) {
        val params = RemoveSearchRequestUseCase.Params.create(id)
        ioToUnit { removeSearchRequestUseCase.execute(params) }
    }
}