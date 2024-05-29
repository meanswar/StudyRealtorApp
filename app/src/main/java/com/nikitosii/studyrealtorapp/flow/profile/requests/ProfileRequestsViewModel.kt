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
    getLocalRequestsUseCase: GetLocalRequestsUseCase,
    private val updateRequestUseCase: UpdateRequestUseCase,
) : BaseViewModel() {

    val properties = getLocalRequestsUseCase.execute().toWorkLiveData()

    fun updateRequest(data: SearchRequest) {
        val params = UpdateRequestUseCase.Params.create(data.copy(favorite = !data.favorite))
        ioToUnit { updateRequestUseCase.execute(params) }
    }
}