package com.nikitosii.studyrealtorapp.flow.dashboard.details

import androidx.lifecycle.LiveData
import com.nikitosii.studyrealtorapp.core.domain.WorkLiveData
import com.nikitosii.studyrealtorapp.core.domain.WorkResult
import com.nikitosii.studyrealtorapp.core.source.local.model.Property
import com.nikitosii.studyrealtorapp.core.source.local.model.property_details.PropertyDetails
import com.nikitosii.studyrealtorapp.core.source.useCase.properties.GetPropertyDetailsUseCase
import com.nikitosii.studyrealtorapp.flow.base.BaseViewModel
import javax.inject.Inject

class PropertyDetailsViewModel @Inject constructor(
    private val getPropertyDetailsUseCase: GetPropertyDetailsUseCase
): BaseViewModel() {
    private val _property = WorkLiveData<PropertyDetails>()
    val property: LiveData<WorkResult<PropertyDetails>>
        get() = _property

    fun getPropertyDetails(id: String) {
        val params = GetPropertyDetailsUseCase.Params.create(id)
        ioToUiWorkData(
            io = {  getPropertyDetailsUseCase.execute(params) },
            ui = { _property.postValue(it) }
        )
    }
}