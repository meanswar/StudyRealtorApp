package com.nikitosii.studyrealtorapp.core.domain.repository.base

import com.nikitosii.studyrealtorapp.core.source.channel.Status
import com.nikitosii.studyrealtorapp.core.source.connectivity.ConnectivityProvider
import com.nikitosii.studyrealtorapp.util.Flow
import com.nikitosii.studyrealtorapp.util.ext.asRefreshing
import com.nikitosii.studyrealtorapp.util.ext.asUpToDate
import com.nikitosii.studyrealtorapp.util.ext.sendToChannel
import com.nikitosii.studyrealtorapp.util.ext.setAsOnlyLocal
import com.nikitosii.studyrealtorapp.util.ext.setAsRefreshing
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.coroutines.CoroutineContext

@FlowPreview
@ExperimentalCoroutinesApi
fun <T : Any> repoChannel(
    io: CoroutineDispatcher,
    connectivityProvider: ConnectivityProvider,
    recreateObserver: ChannelRecreateObserver? = null,
    init: RepoChannel<T>.() -> Unit
): Lazy<RepoChannel<T>> {

    return object : Lazy<RepoChannel<T>> {
        private var repoChannel: RepoChannel<T>? = null

        override val value: RepoChannel<T>
            get() {
                if (repoChannel == null) {
                    repoChannel = RepoChannel(io, connectivityProvider)
                    recreateObserver?.observe(repoChannel!!)
                    init(repoChannel!!)
                    repoChannel!!.refresh()
                }
                return repoChannel!!
            }

        override fun isInitialized() = repoChannel != null
    }
}

@FlowPreview
@ExperimentalCoroutinesApi
class RepoChannel<T : Any>(
    private val io: CoroutineDispatcher,
    private val connectivityProvider: ConnectivityProvider
) : CoroutineScope, ConnectivityProvider.ConnectivityStateListener {

    override val coroutineContext: CoroutineContext = io

    private var channel = MutableSharedFlow<Status<T>>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
        extraBufferCapacity = 1
    )

    private var isRefreshing: AtomicBoolean = AtomicBoolean(false)

    private var refresh = false

    val flow: Flow<T>
        get() {
            if (refresh) {
                refresh()
                refresh = false
            }
            return channel.asSharedFlow()
        }

    private var storageConfig: StorageConfig<T>? = null
    private lateinit var networkConfig: NetworkConfig<T>
    private var refreshAfterNetworkConnected = false

    init {
        connectivityProvider.addListener(this)
    }

    override fun onStateChange(state: ConnectivityProvider.NetworkState) {
        when (state) {
            ConnectivityProvider.NetworkState.NotConnectedState -> {
                Timber.d("Repo in non connected state")
            }

            is ConnectivityProvider.NetworkState.ConnectedState -> {
                if (refreshAfterNetworkConnected && state.hasInternet) {
                    Timber.d("Repo in connected state, having a task to refresh...")
                    refreshOnlyNetwork()
                }
            }
        }
    }

    fun storageConfig(init: StorageConfig<T>.() -> Unit): StorageConfig<T> {
        storageConfig = StorageConfig()
        init(storageConfig as StorageConfig<T>)
        return storageConfig as StorageConfig<T>
    }

    fun refreshOnlyNetwork() {
        launch(io) {
            internalRefreshOnlyNetwork(true)
        }
    }

    fun refresh() {
        launch(io) {
            val res = storageConfig?.get?.invoke()?.asRefreshing()
            res?.sendToChannel(channel)
            internalRefreshOnlyNetwork(false)
        }
    }

    fun refreshOnlyLocal() {
        launch(io) {
            storageConfig?.get?.invoke()
                ?.asUpToDate()
                ?.sendToChannel(channel)
        }
    }

    private suspend fun internalRefreshOnlyNetwork(setRefreshingBeforeCall: Boolean) {
        if (isRefreshing.get()) return
        isRefreshing.set(true)
        refreshAfterNetworkConnected = when (connectivityProvider.getNetworkState()) {
            ConnectivityProvider.NetworkState.NotConnectedState -> {
                channel.setAsOnlyLocal()
                isRefreshing.set(false)
                true
            }

            is ConnectivityProvider.NetworkState.ConnectedState -> try {
                if (setRefreshingBeforeCall)
                    channel.setAsRefreshing()
                networkConfig.get().run {
                    storageConfig?.save?.invoke(this)
                    this.asUpToDate().sendToChannel(channel)
                    isRefreshing.set(false)
                }
                false
            } catch (e: Exception) {
                channel.setAsOnlyLocal()
                isRefreshing.set(false)
                Timber.e(e)
                true
            }
        }
    }
}

class StorageConfig<T> {
    var save: (suspend (T) -> Unit)? = null
    var get: (suspend () -> T)? = null
}

class NetworkConfig<T> {
    lateinit var get: (suspend () -> T)
}

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
class ChannelRecreateObserver {
    private val observers = mutableListOf<RepoChannel<*>?>()

    fun observe(repoChannel: RepoChannel<*>) {
        observers.add(repoChannel)
    }
}