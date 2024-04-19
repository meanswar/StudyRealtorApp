package com.nikitosii.studyrealtorapp.flow.sales

import com.nikitosii.studyrealtorapp.core.source.useCase.properties.sale.GetSaleRequestsUseCase
import com.nikitosii.studyrealtorapp.flow.base.BaseViewModel
import javax.inject.Inject

class SalesViewModel @Inject constructor(
    getRequestHistoryUseCase: GetSaleRequestsUseCase
): BaseViewModel() {

    val saleRequestsHistory = getRequestHistoryUseCase.execute().toWorkLiveData()
}