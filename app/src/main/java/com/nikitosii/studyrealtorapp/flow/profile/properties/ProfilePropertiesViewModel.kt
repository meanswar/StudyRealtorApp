package com.nikitosii.studyrealtorapp.flow.profile.properties

import androidx.lifecycle.MutableLiveData
import com.nikitosii.studyrealtorapp.core.domain.useCase.properties.GetAllLocalPropertiesUseCase
import com.nikitosii.studyrealtorapp.core.domain.useCase.properties.RemovePropertyUseCase
import com.nikitosii.studyrealtorapp.core.domain.useCase.properties.UpdatePropertyUseCase
import com.nikitosii.studyrealtorapp.core.source.local.model.Property
import com.nikitosii.studyrealtorapp.di.modules.AppModule
import com.nikitosii.studyrealtorapp.flow.base.BaseViewModel
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject
import javax.inject.Named


class ProfilePropertiesViewModel @Inject constructor(
    getLocalPropertiesUseCase: GetAllLocalPropertiesUseCase,
    private val removePropertyUseCase: RemovePropertyUseCase,
    private val updatePropertyUseCase: UpdatePropertyUseCase,
    @Named(AppModule.IO_DISPATCHER) ioDispatcher: CoroutineDispatcher,
    @Named(AppModule.MAIN_DISPATCHER) uiDispatcher: CoroutineDispatcher
) : BaseViewModel(ioDispatcher, uiDispatcher) {
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