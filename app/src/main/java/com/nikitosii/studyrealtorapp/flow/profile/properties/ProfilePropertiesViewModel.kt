package com.nikitosii.studyrealtorapp.flow.profile.properties

import androidx.lifecycle.LiveData
import com.nikitosii.studyrealtorapp.core.domain.WorkLiveData
import com.nikitosii.studyrealtorapp.core.domain.WorkResult
import com.nikitosii.studyrealtorapp.core.source.local.model.Property
import com.nikitosii.studyrealtorapp.core.source.useCase.properties.GetAllLocalPropertiesUseCase
import com.nikitosii.studyrealtorapp.core.source.useCase.properties.UpdatePropertyUseCase
import com.nikitosii.studyrealtorapp.flow.base.BaseViewModel
import javax.inject.Inject


class ProfilePropertiesViewModel @Inject constructor(
    private val getLocalPropertiesUseCase: GetAllLocalPropertiesUseCase,
    private val updatePropertyUseCase: UpdatePropertyUseCase,
) : BaseViewModel() {

    private val _properties = WorkLiveData<List<Property>>()
    val properties: LiveData<WorkResult<List<Property>>>
        get() = _properties

    fun getLocalAgents() = ioToUiWorkData(
        io = { getLocalPropertiesUseCase.execute() },
        ui = { _properties.postValue(it) }
    )

    fun updateProperty(data: Property) {
        val params = UpdatePropertyUseCase.Params.create(data.copy(favorite = !data.favorite))
        ioToUnit { updatePropertyUseCase.execute(params) }
    }
}