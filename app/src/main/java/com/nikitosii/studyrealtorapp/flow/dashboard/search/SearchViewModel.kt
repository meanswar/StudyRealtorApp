package com.nikitosii.studyrealtorapp.flow.dashboard.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nikitosii.studyrealtorapp.core.domain.WorkLiveData
import com.nikitosii.studyrealtorapp.core.domain.WorkResult
import com.nikitosii.studyrealtorapp.core.source.local.model.HouseType
import com.nikitosii.studyrealtorapp.core.source.local.model.Property
import com.nikitosii.studyrealtorapp.core.source.local.model.request.RequestType
import com.nikitosii.studyrealtorapp.core.source.local.model.request.SearchRequest
import com.nikitosii.studyrealtorapp.core.source.useCase.properties.GetLocalPropertiesUseCase
import com.nikitosii.studyrealtorapp.core.source.useCase.properties.GetLocalPropertyUseCase
import com.nikitosii.studyrealtorapp.core.source.useCase.properties.UpdatePropertyUseCase
import com.nikitosii.studyrealtorapp.core.source.useCase.properties.rent.GetPropertiesForRentUseCase
import com.nikitosii.studyrealtorapp.core.source.useCase.properties.sale.GetPropertiesForSaleUseCase
import com.nikitosii.studyrealtorapp.flow.base.BaseViewModel
import javax.inject.Inject

class SearchViewModel @Inject constructor(
    private val getPropertiesForSaleUseCase: GetPropertiesForSaleUseCase,
    private val getPropertiesForRentUseCase: GetPropertiesForRentUseCase,
    private val getLocalPropertiesUseCase: GetLocalPropertiesUseCase,
    private val updatePropertyUseCase: UpdatePropertyUseCase,
    private val getLocalPropertyUseCase: GetLocalPropertyUseCase
) : BaseViewModel() {

    private val request = MutableLiveData<SearchRequest>()

    private val filterHouses = mutableListOf<HouseType>()
    val addressFilter by lazy { MutableLiveData<String>() }
    val priceMinFilter by lazy { MutableLiveData<Int>() }
    val priceMaxFilter by lazy { MutableLiveData<Int>() }
    val bedsMinFilter by lazy { MutableLiveData<Int>() }
    val bedsMaxFilter by lazy { MutableLiveData<Int>() }
    val bathsMinFilter by lazy { MutableLiveData<Int>() }
    val bathsMaxFilter by lazy { MutableLiveData<Int>() }
    val sqftMinFilter by lazy { MutableLiveData<Int>() }
    val sqftMaxFilter by lazy { MutableLiveData<Int>() }
    val requestType = MutableLiveData<RequestType>()

    private val _properties = WorkLiveData<Pair<SearchRequest, List<Property>>>()

    val isDataAlreadyUploaded = MutableLiveData(false)
    private val isNeedToUpdateLocalPropertyData = MutableLiveData(false)

    val properties: LiveData<WorkResult<Pair<SearchRequest, List<Property>>>>
        get() = _properties

    val localProperties = WorkLiveData<List<Property>>()
    val openedPropertyId = MutableLiveData<String>()
    val updatedProperty = MutableLiveData<Property>()

    fun setSearchRequest(request: SearchRequest) {
        this.request.value = request
        addressFilter.value = request.address
        priceMinFilter.value = request.priceMin
        priceMaxFilter.value = request.priceMax
        bedsMinFilter.value = request.bedsMin
        bedsMaxFilter.value = request.bedsMax
        bathsMinFilter.value = request.bathsMin
        bathsMaxFilter.value = request.bathsMax
        sqftMinFilter.value = request.sqftMin
        requestType.value = request.requestType
    }

    fun isNeedToUpdateLocalProperty(): Boolean = isNeedToUpdateLocalPropertyData.value == true

    fun setNeedToUpdateLocalProperty(isNeed: Boolean) {
        isNeedToUpdateLocalPropertyData.value = isNeed
    }


    fun updateSaleRequest(id: Int? = null): SearchRequest {
        val newRequest = request.value?.copy(
            id = id,
            address = addressFilter.value?.replaceFirstChar(Char::titlecase) ?: "",
            houses = filterHouses,
            priceMin = priceMinFilter.value,
            priceMax = priceMaxFilter.value,
            bedsMin = bedsMinFilter.value,
            bedsMax = bedsMaxFilter.value,
            bathsMin = bathsMinFilter.value,
            bathsMax = bathsMaxFilter.value,
            sqftMin = sqftMinFilter.value,
            sqftMax = sqftMaxFilter.value,
            requestType = requestType.value!!,
        ) ?: SearchRequest.emptyInstance()
        request.postValue(newRequest)
        return newRequest
    }

    private fun checkFilters(): Boolean {
        return ((!addressFilter.value.isNullOrEmpty()
                || filterHouses.isNotEmpty()) || (priceMinFilter.value
            ?: 0) > 0 || (priceMaxFilter.value ?: 0) > 0 || (bedsMinFilter.value
            ?: 0) > 0 || (bedsMaxFilter.value ?: 0) > 0 || (bathsMinFilter.value
            ?: 0) > 0 || (bathsMaxFilter.value ?: 0) > 0 || (sqftMinFilter.value
            ?: 0) > 0 || (sqftMaxFilter.value ?: 0) > 0)
    }

    fun getPropertiesByRequest() {
        val request = request.value?.requestType
        if (request == RequestType.SALE) getPropertiesForSale()
        else getPropertiesForRent()
    }

    fun getPropertiesForSale() {
        if (checkFilters()) {
            val data = request.value ?: return
            val params = GetPropertiesForSaleUseCase.Params.create(data)
            ioToUiWorkData(
                io = { getPropertiesForSaleUseCase.execute(params) },
                ui = { _properties.postValue(it) }
            )
        }
    }

    fun getPropertiesForRent() {
        if (checkFilters()) {
            val data = request.value ?: return
            val params = GetPropertiesForRentUseCase.Params.create(data)
            ioToUiWorkData(
                io = { getPropertiesForRentUseCase.execute(params) },
                ui = { _properties.postValue(it) }
            )
        }
    }

    fun getLocalProperties(request: SearchRequest) {
        val id = request.id
        id?.let {
            val params = GetLocalPropertiesUseCase.Params.from(id)
            ioToUiWorkData(
                io = { getLocalPropertiesUseCase.execute(params) },
                ui = { localProperties.postValue(it) }
            )
        }
    }

    fun setFilterHouse(house: HouseType): Boolean {
        return if (filterHouses.contains(house)) {
            filterHouses.remove(house)
            false
        } else {
            filterHouses.add(house)
            true
        }
    }

    fun onFavoriteClick(data: Property) {
        val params = UpdatePropertyUseCase.Params.create(data.copy(favorite = !data.favorite))
        ioToUnit {
            updatePropertyUseCase.execute(params)
        }
    }

    fun getUpdatedProperty() {
        val id = openedPropertyId.value ?: return
        val params = GetLocalPropertyUseCase.Params.create(id)
        ioToUi(
            io = { getLocalPropertyUseCase.execute(params) },
            ui = { updatedProperty.postValue(it) }
        )
    }
}