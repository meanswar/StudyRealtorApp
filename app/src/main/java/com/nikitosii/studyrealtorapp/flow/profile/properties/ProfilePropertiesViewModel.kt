package com.nikitosii.studyrealtorapp.flow.profile.properties

import com.nikitosii.studyrealtorapp.core.source.local.model.Property
import com.nikitosii.studyrealtorapp.core.source.useCase.properties.GetAllLocalPropertiesUseCase
import com.nikitosii.studyrealtorapp.core.source.useCase.properties.UpdatePropertyUseCase
import com.nikitosii.studyrealtorapp.flow.base.BaseViewModel
import javax.inject.Inject


class ProfilePropertiesViewModel @Inject constructor(
    getLocalPropertiesUseCase: GetAllLocalPropertiesUseCase,
    private val updatePropertyUseCase: UpdatePropertyUseCase,
) : BaseViewModel() {
    val properties = getLocalPropertiesUseCase.execute().toWorkLiveData()

    fun updateProperty(data: Property) {
        val params = UpdatePropertyUseCase.Params.create(data.copy(favorite = !data.favorite))
        ioToUnit { updatePropertyUseCase.execute(params) }
    }
}