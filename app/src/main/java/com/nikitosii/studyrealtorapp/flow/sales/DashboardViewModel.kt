package com.nikitosii.studyrealtorapp.flow.sales

import com.nikitosii.studyrealtorapp.core.source.useCase.properties.sale.GetRecentSaleRequestsUseCase
import com.nikitosii.studyrealtorapp.flow.base.BaseViewModel
import javax.inject.Inject

class DashboardViewModel @Inject constructor(
    getRequestHistoryUseCase: GetRecentSaleRequestsUseCase
): BaseViewModel() {

    private val filterHouses = mutableListOf<String>()

    val saleRequestsHistory = getRequestHistoryUseCase.execute().toWorkLiveData()

    fun setFilterHouse(house: String): Boolean {
        return if (filterHouses.contains(house)) {
            filterHouses.remove(house)
            false
        } else {
            filterHouses.add(house)
            true
        }
    }
}