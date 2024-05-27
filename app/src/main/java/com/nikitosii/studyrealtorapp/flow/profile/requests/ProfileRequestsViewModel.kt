package com.nikitosii.studyrealtorapp.flow.profile.requests

import androidx.lifecycle.LiveData
import com.nikitosii.studyrealtorapp.core.domain.WorkLiveData
import com.nikitosii.studyrealtorapp.core.domain.WorkResult
import com.nikitosii.studyrealtorapp.core.source.local.model.request.SearchRequest
import com.nikitosii.studyrealtorapp.core.source.useCase.request.GetLocalRequestsUseCase
import com.nikitosii.studyrealtorapp.core.source.useCase.request.UpdateRequestUseCase
import com.nikitosii.studyrealtorapp.flow.base.BaseViewModel
import javax.inject.Inject


class ProfileRequestsViewModel @Inject constructor(
    private val getLocalRequestsUseCase: GetLocalRequestsUseCase,
    private val updateRequestUseCase: UpdateRequestUseCase,
) : BaseViewModel() {

    private val _properties = WorkLiveData<List<SearchRequest>>()
    val properties: LiveData<WorkResult<List<SearchRequest>>>
        get() = _properties

    fun getLocalRequests() = ioToUiWorkData(
        io = { getLocalRequestsUseCase.execute() },
        ui = { _properties.postValue(it) }
    )

    fun updateRequest(data: SearchRequest) {
        val params = UpdateRequestUseCase.Params.create(data.copy(favorite = !data.favorite))
        ioToUnit { updateRequestUseCase.execute(params) }
    }
}