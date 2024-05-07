package com.nikitosii.studyrealtorapp.flow.dashboard

import androidx.lifecycle.MutableLiveData
import com.nikitosii.studyrealtorapp.core.source.local.model.HouseType
import com.nikitosii.studyrealtorapp.core.source.local.model.request.PropertyRequest
import com.nikitosii.studyrealtorapp.core.source.useCase.properties.sale.GetRecentSaleRequestsUseCase
import com.nikitosii.studyrealtorapp.flow.base.BaseViewModel
import javax.inject.Inject

class DashboardViewModel @Inject constructor(
    getRequestHistoryUseCase: GetRecentSaleRequestsUseCase
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
    val requestType  = MutableLiveData("sale")

    fun isFilterHousesFilled(): Boolean = run { filterHouses.isNotEmpty() }

    fun checkFilters(): Boolean {
        return ((!addressFilter.value.isNullOrEmpty()
                || filterHouses.isNotEmpty()) || (priceMinFilter.value ?: 0) > 0 || (priceMaxFilter.value ?: 0) > 0 || (bedsMinFilter.value ?: 0) > 0 || (bedsMaxFilter.value ?: 0) > 0 || (bathsMinFilter.value ?: 0) > 0 || (bathsMaxFilter.value ?: 0) > 0 || (sqftMinFilter.value ?: 0) > 0 || (sqftMaxFilter.value ?: 0) > 0)
    }

    fun buildSaleRequest(): PropertyRequest {
        return PropertyRequest(
            addressFilter.value?.replaceFirstChar(Char::titlecase) ?: "",
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

    val saleRequestsHistory = getRequestHistoryUseCase.execute().toWorkLiveData()

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