package com.nikitosii.studyrealtorapp.flow.profile.requests

import androidx.lifecycle.MutableLiveData
import com.nikitosii.studyrealtorapp.core.source.local.model.request.SearchRequest
import com.nikitosii.studyrealtorapp.core.source.useCase.request.GetLocalRequestsUseCase
import com.nikitosii.studyrealtorapp.core.source.useCase.request.RemoveSearchRequestUseCase
import com.nikitosii.studyrealtorapp.core.source.useCase.request.UpdateRequestUseCase
import com.nikitosii.studyrealtorapp.flow.base.BaseViewModel
import javax.inject.Inject


class ProfileRequestsViewModel @Inject constructor(
    getLocalRequestsUseCase: GetLocalRequestsUseCase,
    private val updateRequestUseCase: UpdateRequestUseCase,
    private val removeSearchRequestUseCase: RemoveSearchRequestUseCase
) : BaseViewModel() {

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