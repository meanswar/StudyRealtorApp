package com.nikitosii.studyrealtorapp.flow.dashboard.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nikitosii.studyrealtorapp.core.source.local.model.request.PropertyRequest
import com.nikitosii.studyrealtorapp.core.source.useCase.properties.sale.GetSaleRequestsUseCase
import com.nikitosii.studyrealtorapp.flow.base.BaseViewModel
import javax.inject.Inject

class SearchSalesHistoryViewModel @Inject constructor(
    private val getSaleRequestsUseCase: GetSaleRequestsUseCase
) : BaseViewModel() {

    private val _searchHistory = MutableLiveData<List<PropertyRequest>>()
    val searchHistory: LiveData<List<PropertyRequest>>
        get() = _searchHistory

    fun getSearchHistory() = ioToUi(
        io = { getSaleRequestsUseCase.execute() },
        ui = { _searchHistory.postValue(it) }
    )
}