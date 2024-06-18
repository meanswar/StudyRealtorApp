package com.nikitosii.studyrealtorapp.di

import android.content.Context
import android.content.res.Resources
import android.net.NetworkCapabilities
import com.nikitosii.studyrealtorapp.TestConstants
import com.nikitosii.studyrealtorapp.core.connectivity.TestConnectivityProvider
import com.nikitosii.studyrealtorapp.core.source.connectivity.ConnectivityProvider
import com.nikitosii.studyrealtorapp.core.domain.repository.base.ChannelRecreateObserver
import com.nikitosii.studyrealtorapp.di.db.TestDataBaseModule
import com.nikitosii.studyrealtorapp.di.modules.AppModule
import com.nikitosii.studyrealtorapp.di.modules.RepoModule
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.mockito.BDDMockito
import org.mockito.Mockito
import javax.inject.Named
import javax.inject.Singleton

@FlowPreview
@ExperimentalCoroutinesApi
@Module(
    includes = [
        RepoModule::class,
        TestStorageModule::class,
        TestDataBaseModule::class,
        TestNetModule::class
    ]
)
class TestAppModule {

    private val testCoroutineDispatcher = UnconfinedTestDispatcher()

    private val mockContext = Mockito.mock(Context::class.java)
    private val mockResources = Mockito.mock(Resources::class.java)
    private val mockNetworkCapabilities = Mockito.mock(NetworkCapabilities::class.java)

    init {
        BDDMockito.lenient().`when`(mockContext.getString(BDDMockito.anyInt(), BDDMockito.any()))
            .thenReturn(
                TestConstants.ANY_TEXT
            )
        BDDMockito.lenient()
            .`when`(mockNetworkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET))
            .thenReturn(true)
    }

    @Provides
    @Singleton
    @Named(AppModule.IO_DISPATCHER)
    internal fun io(): CoroutineDispatcher = testCoroutineDispatcher

    @Provides
    @Singleton
    @Named(AppModule.MAIN_DISPATCHER)
    internal fun main(): CoroutineDispatcher = testCoroutineDispatcher

    @Provides
    @Singleton
    @Named(TEST_DISPATCHER)
    internal fun test(): TestDispatcher = testCoroutineDispatcher

    @Provides
    @Singleton
    internal fun providesContext(): Context = mockContext

    @Provides
    @Singleton
    internal fun providesResources(): Resources = mockResources

    @Provides
    @Singleton
    internal fun providesMoshi() = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    @Provides
    @Singleton
    internal fun providesChannelRecreateObserver() = ChannelRecreateObserver()

    @Provides
    @Singleton
    internal fun connectivityProvider(networkCapabilities: NetworkCapabilities): ConnectivityProvider =
        TestConnectivityProvider(networkCapabilities)

    @Provides
    @Singleton
    internal fun networkCapabilities(): NetworkCapabilities = mockNetworkCapabilities

    companion object {
        const val TEST_DISPATCHER = "TEST"
    }
}