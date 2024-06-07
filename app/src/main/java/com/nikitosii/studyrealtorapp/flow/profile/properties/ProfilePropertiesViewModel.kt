package com.nikitosii.studyrealtorapp.flow.profile.properties

import androidx.lifecycle.MutableLiveData
import com.nikitosii.studyrealtorapp.core.source.local.model.Property
import com.nikitosii.studyrealtorapp.core.source.useCase.properties.GetAllLocalPropertiesUseCase
import com.nikitosii.studyrealtorapp.core.source.useCase.properties.RemovePropertyUseCase
import com.nikitosii.studyrealtorapp.core.source.useCase.properties.UpdatePropertyUseCase
import com.nikitosii.studyrealtorapp.flow.base.BaseViewModel
import javax.inject.Inject


class ProfilePropertiesViewModel @Inject constructor(
    getLocalPropertiesUseCase: GetAllLocalPropertiesUseCase,
    private val removePropertyUseCase: RemovePropertyUseCase,
    private val updatePropertyUseCase: UpdatePropertyUseCase,
) : BaseViewModel() {
    val propertiesNetwork = getLocalPropertiesUseCase.execute().toWorkLiveData()

    val properties = MutableLiveData<List<Property>>()

    fun updateProperty(data: Property) {
        val params = UpdatePropertyUseCase.Params.create(data.copy(favorite = !data.favorite))
        ioToUnit { updatePropertyUseCase.execute(params) }
    }

    fun removePropertyById(id: String) = ioToUnit {
        val params = RemovePropertyUseCase.Params.create(id)
        removePropertyUseCase.execute(params)
    }
}