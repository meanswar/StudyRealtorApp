package com.nikitosii.studyrealtorapp.flow.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nikitosii.studyrealtorapp.core.domain.WorkLiveData
import com.nikitosii.studyrealtorapp.core.domain.WorkResult
import com.nikitosii.studyrealtorapp.core.source.local.model.Coordinate
import com.nikitosii.studyrealtorapp.core.source.local.model.Photo
import com.nikitosii.studyrealtorapp.core.source.local.model.Property
import com.nikitosii.studyrealtorapp.core.source.local.model.parcelize.PhotoContainer
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

    private val _isMapReady = MutableLiveData(false)
    val isMapReady: LiveData<Boolean>
        get() = _isMapReady

    val coordinates = MutableLiveData<Coordinate>()

    val propertyAddressDetails = MutableLiveData<String>()

    val photos = MutableLiveData<List<Photo>>()

    fun getPropertyDetails(id: String) {
        val params = GetPropertyDetailsUseCase.Params.create(id)
        ioToUiWorkData(
            io = {  getPropertyDetailsUseCase.execute(params) },
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
}