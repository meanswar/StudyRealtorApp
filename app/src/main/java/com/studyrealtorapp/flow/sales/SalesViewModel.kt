package com.studyrealtorapp.flow.sales

import com.studyrealtorapp.core.source.useCase.properties.GetPropertiesUseCase
import com.studyrealtorapp.flow.base.BaseViewModel
import javax.inject.Inject

class SalesViewModel @Inject constructor(
    private val getPropertiesUseCase: GetPropertiesUseCase): BaseViewModel() {


}