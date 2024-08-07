package com.nikitosii.studyrealtorapp.core.source.connectivity

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.os.Build
import androidx.annotation.RequiresApi
import com.nikitosii.studyrealtorapp.core.source.connectivity.impl.ConnectivityProviderImpl

interface ConnectivityProvider {
    interface ConnectivityStateListener {
        fun onStateChange(state: NetworkState)
    }

    fun addListener(listener: ConnectivityStateListener)
    fun removeListener(listener: ConnectivityStateListener)

    fun getNetworkState(): NetworkState

    @Suppress("MemberVisibilityCanBePrivate", "CanBeParameter")
    sealed class NetworkState {
        object NotConnectedState : NetworkState()

        sealed class ConnectedState(val hasInternet: Boolean) : NetworkState() {

            data class Connected(val capabilities: NetworkCapabilities) : ConnectedState(
                capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            )

            @Suppress("DEPRECATION")
            data class ConnectedLegacy(val networkInfo: NetworkInfo) : ConnectedState(
                networkInfo.isConnectedOrConnecting
            )
        }
    }

    companion object {
        fun createProvider(context: Context): ConnectivityProvider {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            return ConnectivityProviderImpl(cm)
        }
    }
}