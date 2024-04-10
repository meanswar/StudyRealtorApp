package com.nikitosii.studyrealtorapp.flow.sales.filter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nikitosii.studyrealtorapp.core.domain.WorkLiveData
import com.nikitosii.studyrealtorapp.core.domain.WorkResult
import com.nikitosii.studyrealtorapp.core.source.local.model.Property
import com.nikitosii.studyrealtorapp.core.source.local.model.request.SalesRequest
import com.nikitosii.studyrealtorapp.core.source.useCase.properties.GetPropertiesForSaleUseCase
import com.nikitosii.studyrealtorapp.flow.base.BaseViewModel
import javax.inject.Inject

class FilterSalesViewModel @Inject constructor(
    private val getPropertiesForSaleUseCase: GetPropertiesForSaleUseCase
): BaseViewModel() {
    private val filterHouses = mutableListOf<String>()

    val addressFilter by lazy { MutableLiveData<String>() }
    val priceMinFilter by lazy { MutableLiveData<Int>() }
    val priceMaxFilter by lazy { MutableLiveData<Int>() }
    val bedsMinFilter by lazy { MutableLiveData<Int>() }
    val bedsMaxFilter by lazy { MutableLiveData<Int>() }
    val bathsMinFilter by lazy { MutableLiveData<Int>() }
    val bathsMaxFilter by lazy { MutableLiveData<Int>() }
    val sqftMinFilter by lazy { MutableLiveData<Int>() }
    val sqftMaxFilter by lazy { MutableLiveData<Int>() }

    private val _properties = WorkLiveData<List<Property>>()
    val properties: LiveData<WorkResult<List<Property>>>
        get() = _properties

    fun setFilterHouse(house: String): Boolean {
        return if (filterHouses.contains(house)) {
            filterHouses.remove(house)
            false
        } else {
            filterHouses.add(house)
            true
        }
    }

    private fun buildRequest(): SalesRequest {
        return SalesRequest.create(
            address = addressFilter.value,
            priceMin = priceMinFilter.value,
            priceMax = priceMaxFilter.value,
            bedsMin = bedsMinFilter.value,
            bedsMax = bedsMaxFilter.value,
            bathsMin = bathsMinFilter.value,
            bathsMax = bathsMaxFilter.value,
            sqftMin = sqftMinFilter.value,
            sqftMax = sqftMaxFilter.value,
            houses = filterHouses
        )
    }

    fun findProperties() {
        val params = GetPropertiesForSaleUseCase.Params.create(buildRequest())
        ioToUiWorkData(
            io = { getPropertiesForSaleUseCase.execute(params) },
            ui = { _properties.postValue(it) }
        )
    }
}