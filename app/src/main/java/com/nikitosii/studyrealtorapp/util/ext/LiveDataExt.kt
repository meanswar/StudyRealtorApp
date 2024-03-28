package com.nikitosii.studyrealtorapp.util.ext

import androidx.lifecycle.MutableLiveData
import com.nikitosii.studyrealtorapp.core.domain.WorkResult

fun <T> MutableLiveData<WorkResult<T>>.data() = this.value?.data
fun <T> MutableLiveData<WorkResult<Pair<List<T>, Boolean>>>.add(pair: Pair<List<T>, Boolean>?) {
    val (items, hasMoreItems) = pair ?: (emptyList<T>() to false)
    val storedItems = this.data()?.first
    this.value = WorkResult.success(
        if (storedItems.isNullOrEmpty()) items to hasMoreItems
        else storedItems.plus(items) to hasMoreItems
    )
}

fun <T> MutableLiveData<WorkResult<List<T>>>.add(items: List<T>?) {
    val internalItems = items ?: emptyList()
    val data = this.data()
    this.value = WorkResult.success(
        if (data.isNullOrEmpty()) internalItems
        else data.plus(internalItems)
    )
}