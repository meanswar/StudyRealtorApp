package com.nikitosii.studyrealtorapp.flow.dashboard

import androidx.lifecycle.MutableLiveData
import com.nikitosii.studyrealtorapp.core.source.local.model.HouseType
import com.nikitosii.studyrealtorapp.core.source.local.model.request.RequestType
import com.nikitosii.studyrealtorapp.core.source.local.model.request.SearchRequest
import com.nikitosii.studyrealtorapp.core.source.useCase.profile.GetProfileFlowUseCase
import com.nikitosii.studyrealtorapp.core.source.useCase.request.GetRecentRentSearchRequestsUseCase
import com.nikitosii.studyrealtorapp.core.source.useCase.request.GetRecentSaleSearchRequestsUseCase
import com.nikitosii.studyrealtorapp.core.source.useCase.request.UpdateRequestUseCase
import com.nikitosii.studyrealtorapp.flow.base.BaseViewModel
import javax.inject.Inject

class DashboardViewModel @Inject constructor(
    getRecentSaleSearchRequestsUseCase: GetRecentSaleSearchRequestsUseCase,
    getRecentRentSearchRequestsUseCase: GetRecentRentSearchRequestsUseCase,
    getProfileFlowUseCase: GetProfileFlowUseCase,
    private val updateSearchRequestUseCase: UpdateRequestUseCase
) : BaseViewModel() {

    val recentSaleRequests = getRecentSaleSearchRequestsUseCase.execute().toWorkLiveData()
    val recentRentRequests = getRecentRentSearchRequestsUseCase.execute().toWorkLiveData()
    val profile = getProfileFlowUseCase.execute().toWorkLiveData()

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

    fun isFilterHousesFilled(): Boolean = run { filterHouses.isNotEmpty() }

    fun checkFilters(): Boolean {
        return ((!addressFilter.value.isNullOrEmpty()
                || filterHouses.isNotEmpty()) || (priceMinFilter.value
            ?: 0) > 0 || (priceMaxFilter.value ?: 0) > 0 || (bedsMinFilter.value
            ?: 0) > 0 || (bedsMaxFilter.value ?: 0) > 0 || (bathsMinFilter.value
            ?: 0) > 0 || (bathsMaxFilter.value ?: 0) > 0 || (sqftMinFilter.value
            ?: 0) > 0 || (sqftMaxFilter.value ?: 0) > 0)
    }

    fun buildSaleRequest(): SearchRequest {
        return SearchRequest(
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
            requestType = requestType.value!!
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

    fun setRequestType(requestType: RequestType) {
        this.requestType.value = requestType
    }

    fun updateRequest(request: SearchRequest) {
        val params = UpdateRequestUseCase.Params.create(request)
        ioToUnit(io = { updateSearchRequestUseCase.execute(params) })
    }
}