package com.nikitosii.studyrealtorapp.flow.sales

import com.nikitosii.studyrealtorapp.core.source.useCase.properties.sale.GetRecentSaleRequestsUseCase
import com.nikitosii.studyrealtorapp.flow.base.BaseViewModel
import javax.inject.Inject

class DashboardViewModel @Inject constructor(
    getRequestHistoryUseCase: GetRecentSaleRequestsUseCase
): BaseViewModel() {

    val saleRequestsHistory = getRequestHistoryUseCase.execute().toWorkLiveData()
}