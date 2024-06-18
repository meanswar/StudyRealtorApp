package com.nikitosii.studyrealtorapp.flow.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nikitosii.studyrealtorapp.core.domain.WorkLiveData
import com.nikitosii.studyrealtorapp.core.domain.WorkResult
import com.nikitosii.studyrealtorapp.core.domain.useCase.properties.GetPropertyDetailsUseCase
import com.nikitosii.studyrealtorapp.core.domain.useCase.properties.UpdatePropertyUseCase
import com.nikitosii.studyrealtorapp.core.source.local.model.Coordinate
import com.nikitosii.studyrealtorapp.core.source.local.model.Photo
import com.nikitosii.studyrealtorapp.core.source.local.model.Property
import com.nikitosii.studyrealtorapp.core.source.local.model.parcelize.PhotoContainer
import com.nikitosii.studyrealtorapp.core.source.local.model.property_details.PropertyDetails
import com.nikitosii.studyrealtorapp.flow.base.BaseViewModel
import javax.inject.Inject

class PropertyDetailsViewModel @Inject constructor(
    private val getPropertyDetailsUseCase: GetPropertyDetailsUseCase,
    private val updatePropertyUseCase: UpdatePropertyUseCase
) : BaseViewModel() {
    private val _property = WorkLiveData<PropertyDetails>()
    val property: LiveData<WorkResult<PropertyDetails>>
        get() = _property

    val localProperty = MutableLiveData<Property>()

    fun setLocalProperty(property: Property) {
        localProperty.value = property
    }

    private val _isMapReady = MutableLiveData(false)
    val isMapReady: LiveData<Boolean>
        get() = _isMapReady

    val coordinates = MutableLiveData<Coordinate>()

    val propertyAddressDetails = MutableLiveData<String>()

    val photos = MutableLiveData<List<Photo>>()

    fun getPropertyDetails(id: String) {
        val params = GetPropertyDetailsUseCase.Params.create(id)
        ioToUiWorkData(
            io = { getPropertyDetailsUseCase.execute(params) },
            ui = { _property.postValue(it) }
        )
    }

    fun updateMapStatus(isMapReady: Boolean) {
        _isMapReady.postValue(isMapReady)
    }

    fun buildPhotoContainer(imageStarterId: Int): PhotoContainer {
        val photos = photos.value ?: listOf()
        return PhotoContainer(photos, imageStarterId)
    }

    val updateStatus = WorkLiveData<Unit>()

    fun onFavoriteClick() {
        val property = localProperty.value ?: return
        val updatedProperty = property.copy(favorite = !property.favorite)
        localProperty.value = updatedProperty
        val params = UpdatePropertyUseCase.Params.create(updatedProperty)
        ioToUiWorkData(
            io = { updatePropertyUseCase.execute(params) },
            ui = { updateStatus.postValue(it) }
        )
    }
}