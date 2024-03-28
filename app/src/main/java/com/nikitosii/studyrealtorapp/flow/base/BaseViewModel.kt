package com.nikitosii.studyrealtorapp.flow.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.nikitosii.studyrealtorapp.core.domain.Status
import com.nikitosii.studyrealtorapp.core.domain.WorkLiveData
import com.nikitosii.studyrealtorapp.core.domain.WorkResult
import com.nikitosii.studyrealtorapp.util.ext.add
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.net.SocketTimeoutException

open class BaseViewModel(
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val uiDispatcher: CoroutineDispatcher = Dispatchers.Main
): ViewModel() {

    fun ioToUnit(io: suspend () -> Unit) = ioToUi(io, {})

    fun <T> ioToUi(io: suspend () -> T, ui: suspend (T) -> Unit, error: (suspend (error: Throwable) -> Unit)? = null) =
        viewModelScope.launch(ioDispatcher) {
            try {
                val result = io()
                withContext(uiDispatcher) {
                    ui(result)
                }
            } catch (e: Exception) {
                Timber.e(e)
                error?.invoke(e)
                recordExceptionToFirebase(e)
            }
        }

    fun <T> ioToUiWorkData(
        io: suspend () -> T,
        ui: suspend (WorkResult<T>) -> Unit,
        also: (suspend (T) -> Unit)? = null
    ) = viewModelScope.launch(uiDispatcher) {
        ui(WorkResult.loading())
        withContext(ioDispatcher) {
            val result = try {
                WorkResult.success(io().also {
                    also?.invoke(it)
                })
            } catch (e: Exception) {
                Timber.e(e)
                recordExceptionToFirebase(e)
                WorkResult.error<T>(e.message ?: "", exception = e)
            }
            withContext(uiDispatcher) {
                ui(result)
            }
        }
    }

    fun <T> flowToWorkUi(
        flow: Flow<T>,
        ui: suspend (WorkResult<T>) -> Unit,
        also: (suspend (T) -> Unit)? = null
    ): Job {
        return viewModelScope.launch(ioDispatcher) {
            ui(WorkResult.loading())
            try {
                flow.collect { result ->
                    withContext(uiDispatcher) {
                        ui(WorkResult.success(result.also { also?.invoke(it) }))
                    }
                }
            } catch (e: Exception) {
                Timber.e(e)
                recordExceptionToFirebase(e)
                withContext(uiDispatcher) {
                    ui(WorkResult.error(e.message ?: "", exception = e))
                }
            }
        }
    }

    fun <T> flowToUi(flow: Flow<T>, ui: suspend (T) -> Unit) {
        viewModelScope.launch(ioDispatcher) {
            flow.collect {
                withContext(uiDispatcher) {
                    ui(it)
                }
            }
        }
    }

    fun <T> flowToSingleUi(flow: Flow<T>, ui: suspend (T) -> Unit) {
        viewModelScope.launch(ioDispatcher) {
            val result = flow.first()
            withContext(uiDispatcher) {
                ui(result)
            }
        }
    }

    fun <T> flowToLiveData(
        flow: Flow<T>,
        liveData: MutableLiveData<T>
    ) = flowToUi(flow) { liveData.postValue(it) }

    fun <T> flowToWorkLiveData(
        flow: Flow<T>,
        liveData: WorkLiveData<T>,
        also: (suspend (T) -> Unit)? = null
    ) = flowToWorkUi(flow, { liveData.postValue(it) }, also)

    fun <T> flowToSingleLiveData(
        flow: Flow<T>,
        liveData: MutableLiveData<T>
    ) = flowToSingleUi(flow) { liveData.postValue(it) }

    fun <T> cumulativeFlowToLiveData(
        flow: Flow<T>,
        liveData: MutableLiveData<List<T>>
    ) = flowToUi(flow) {
        liveData.postValue((liveData.value?.toMutableList() ?: mutableListOf()).apply {
            add(it)
        })
    }

    fun <T> Flow<T>.toSingleLiveData() = MutableLiveData<T>().also {
        flowToSingleLiveData(this, it)
    }

    fun <T> Flow<T>.toLiveData() = MutableLiveData<T>().also {
        flowToLiveData(this, it)
    }

    fun <T> Flow<T>.toCumulativeLiveData() = MutableLiveData<List<T>>().also {
        cumulativeFlowToLiveData(this, it)
    }

    fun <T> Flow<T>.toWorkLiveData(also: (suspend (T) -> Unit)? = null) = WorkLiveData<T>().also {
        flowToWorkLiveData(this, it, also)
    }

    fun <T> ioToLiveData(
        io: suspend () -> T,
        liveData: MutableLiveData<WorkResult<T>>,
        also: (suspend (T) -> Unit)? = null
    ) = try {
        ioToUiWorkData(io, { liveData.value = it }, also)
    } catch (e: Exception) {
        Timber.e(e)
        recordExceptionToFirebase(e)
        null
    }

    fun <T> ioToLiveDataCollection(
        io: suspend () -> List<T>,
        liveData: MutableLiveData<WorkResult<List<T>>>,
        replace: Boolean = false
    ) = ioToUiWorkData(
        io = io,
        ui = {
            when (it.status) {
                Status.SUCCESS -> {
                    if (replace) {
                        liveData.value = it
                    } else {
                        liveData.add(it.data)
                    }
                }
                Status.ERROR -> liveData.value =
                    WorkResult.error(
                        it.message ?: "",
                        liveData.value?.data,
                        it.exception
                    )
                Status.LOADING -> liveData.value =
                    WorkResult.loading(liveData.value?.data)
            }
        }
    )

    fun <T> ioToLiveDataCollectionWithPaginate(
        io: suspend () -> Pair<List<T>, Boolean>,
        liveData: MutableLiveData<WorkResult<Pair<List<T>, Boolean>>>,
        replace: Boolean = false
    ) = ioToUiWorkData(
        io = io,
        ui = {
            when (it.status) {
                Status.SUCCESS -> {
                    if (replace) {
                        liveData.value = it
                    } else {
                        liveData.add(it.data)
                    }
                }
                Status.ERROR -> liveData.value =
                    WorkResult.error(
                        it.message ?: "",
                        liveData.value?.data,
                        it.exception
                    )
                Status.LOADING -> liveData.value =
                    WorkResult.loading(liveData.value?.data)
            }
        }
    )

    override fun onCleared() {
        super.onCleared()
        viewModelScope.coroutineContext.cancel()
    }

    private fun recordExceptionToFirebase(e: Exception) {
        if (e !is SocketTimeoutException) FirebaseCrashlytics
            .getInstance()
            .recordException(e)
    }

}