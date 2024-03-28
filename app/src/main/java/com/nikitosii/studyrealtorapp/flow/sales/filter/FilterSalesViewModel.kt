package com.nikitosii.studyrealtorapp.flow.sales.filter

import androidx.lifecycle.MutableLiveData
import com.nikitosii.studyrealtorapp.flow.base.BaseViewModel
import javax.inject.Inject

class FilterSalesViewModel @Inject constructor(): BaseViewModel() {
    val addressSearch by lazy { MutableLiveData<String>() }
    private val filterHouses = mutableListOf<String>()


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