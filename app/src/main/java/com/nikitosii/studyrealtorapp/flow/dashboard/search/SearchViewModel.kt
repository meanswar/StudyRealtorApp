package com.nikitosii.studyrealtorapp.flow.dashboard.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nikitosii.studyrealtorapp.core.domain.WorkLiveData
import com.nikitosii.studyrealtorapp.core.domain.WorkResult
import com.nikitosii.studyrealtorapp.core.source.local.model.HouseType
import com.nikitosii.studyrealtorapp.core.source.local.model.Property
import com.nikitosii.studyrealtorapp.core.source.local.model.request.PropertyRequest
import com.nikitosii.studyrealtorapp.core.source.useCase.properties.sale.GetLocalPropertiesForSaleUseCase
import com.nikitosii.studyrealtorapp.core.source.useCase.properties.sale.GetPropertiesForSaleUseCase
import com.nikitosii.studyrealtorapp.flow.base.BaseViewModel
import javax.inject.Inject

class SearchViewModel @Inject constructor(
    private val getSaleRequestsUseCase: GetPropertiesForSaleUseCase,
    private val getLocalPropertiesForSaleUseCase: GetLocalPropertiesForSaleUseCase
) : BaseViewModel() {

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
    val requestType = MutableLiveData("sale")

    fun buildSaleRequest(): PropertyRequest {
        return PropertyRequest(
            addressFilter.value?.replaceFirstChar(Char::titlecase),
            filterHouses,
            priceMinFilter.value,
            priceMaxFilter.value,
            bedsMinFilter.value,
            bedsMaxFilter.value,
            bathsMinFilter.value,
            bathsMaxFilter.value,
            sqftMinFilter.value,
            sqftMaxFilter.value,
            requestType.value!!
        )
    }

    fun getProperties(request: PropertyRequest) {
        addressFilter.value = request.address
        priceMinFilter.value = request.priceMin
        priceMaxFilter.value = request.priceMax
        bedsMinFilter.value = request.bedsMin
        bedsMaxFilter.value = request.bedsMax
        bathsMinFilter.value = request.bathsMin
        bathsMaxFilter.value = request.bathsMax
        sqftMinFilter.value = request.sqftMin
        getProperties()
    }

    private fun checkFilters(): Boolean {
        return ((!addressFilter.value.isNullOrEmpty()
                || filterHouses.isNotEmpty()) || (priceMinFilter.value
            ?: 0) > 0 || (priceMaxFilter.value ?: 0) > 0 || (bedsMinFilter.value
            ?: 0) > 0 || (bedsMaxFilter.value ?: 0) > 0 || (bathsMinFilter.value
            ?: 0) > 0 || (bathsMaxFilter.value ?: 0) > 0 || (sqftMinFilter.value
            ?: 0) > 0 || (sqftMaxFilter.value ?: 0) > 0)
    }

    private val _properties = WorkLiveData<List<Property>>()

    val isDataAlreadyUploaded = MutableLiveData(false)

    val properties: LiveData<WorkResult<List<Property>>>
        get() = _properties

    fun getProperties() {
        if (checkFilters()) {
            val data = buildSaleRequest()
            val params = GetPropertiesForSaleUseCase.Params.create(data)
            ioToUiWorkData(
                io = { getSaleRequestsUseCase.execute(params) },
                ui = { _properties.postValue(it) }
            )
        }
    }

    fun getLocalSaleProperties(request: PropertyRequest) {
        val params = GetLocalPropertiesForSaleUseCase.Params.create(request)
        ioToUiWorkData(
            io = { getLocalPropertiesForSaleUseCase.execute(params) },
            ui = { _properties.postValue(it) }
        )
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
}