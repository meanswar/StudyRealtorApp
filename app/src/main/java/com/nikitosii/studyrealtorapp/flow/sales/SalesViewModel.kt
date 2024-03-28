package com.nikitosii.studyrealtorapp.flow.sales

import com.nikitosii.studyrealtorapp.core.source.useCase.properties.GetPropertiesUseCase
import com.nikitosii.studyrealtorapp.flow.base.BaseViewModel
import javax.inject.Inject

class SalesViewModel @Inject constructor(
    private val getPropertiesUseCase: GetPropertiesUseCase
): BaseViewModel() {


}