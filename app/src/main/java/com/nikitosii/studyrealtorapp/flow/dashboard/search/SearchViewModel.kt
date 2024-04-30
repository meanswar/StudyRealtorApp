package com.nikitosii.studyrealtorapp.flow.dashboard.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nikitosii.studyrealtorapp.core.domain.WorkLiveData
import com.nikitosii.studyrealtorapp.core.domain.WorkResult
import com.nikitosii.studyrealtorapp.core.source.local.model.Property
import com.nikitosii.studyrealtorapp.core.source.local.model.request.SalesRequest
import com.nikitosii.studyrealtorapp.core.source.useCase.properties.sale.GetPropertiesForSaleUseCase
import com.nikitosii.studyrealtorapp.core.source.useCase.properties.sale.GetLocalPropertiesForSaleUseCase
import com.nikitosii.studyrealtorapp.flow.base.BaseViewModel
import javax.inject.Inject

class SearchViewModel @Inject constructor(
    private val getSaleRequestsUseCase: GetPropertiesForSaleUseCase,
    private val getLocalPropertiesForSaleUseCase: GetLocalPropertiesForSaleUseCase
) : BaseViewModel() {
    private val _saleProperties = WorkLiveData<List<Property>>()

    val isDataAlreadyUploaded = MutableLiveData(false)

    val saleProperties: LiveData<WorkResult<List<Property>>>
        get() = _saleProperties

    fun getSaleProperties(request: SalesRequest) {
        val params = GetPropertiesForSaleUseCase.Params.create(request)
        ioToUiWorkData(
            io = { getSaleRequestsUseCase.execute(params) },
            ui = { _saleProperties.postValue(it) }
        )
    }

    fun getLocalSaleProperties(request: SalesRequest) {
        val params = GetLocalPropertiesForSaleUseCase.Params.create(request)
        ioToUiWorkData(
            io = { getLocalPropertiesForSaleUseCase.execute(params) },
            ui = { _saleProperties.postValue(it) }
        )
    }
}