package com.nikitosii.studyrealtorapp.di.modules

import android.app.Application
import android.content.Context
import android.content.res.Resources
import com.google.gson.Gson
import com.nikitosii.studyrealtorapp.core.source.connectivity.ConnectivityProvider
import com.nikitosii.studyrealtorapp.core.source.repository.base.ChannelRecreateObserver
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import javax.inject.Named
import javax.inject.Singleton

@Module(
    includes = [
        InitializerModule::class,
        ActivityModule::class,
        FragmentModule::class,
        ViewModelModule::class,
        RepoModule::class,
        NetworkModule::class,
        NetworkErrorModule::class,
        DataBaseModule::class
    ]
)
class AppModule {

    @Provides
    @Singleton
    @Named(IO_DISPATCHER)
    internal fun io(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @Singleton
    @Named(MAIN_DISPATCHER)
    internal fun main(): CoroutineDispatcher = Dispatchers.Main

    @Provides
    internal fun providesContext(application: Application): Context = application.applicationContext

    @Provides
    internal fun providesResources(application: Application): Resources = application.resources

    @Provides
    @Singleton
    internal fun providesMoshi() = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    @Provides
    @Singleton
    internal fun provideGson() = Gson().newBuilder().create()

    @Provides
    @Singleton
    internal fun providesChannelRecreateObserver() = ChannelRecreateObserver()

    @Provides
    @Singleton
    internal fun connectivityProvider(context: Context) =
        ConnectivityProvider.createProvider(context)

    @Provides
    @Singleton
    internal fun provideApplicationCoroutineScope() = CoroutineScope(Job() + Dispatchers.Default)

    companion object {
        const val IO_DISPATCHER = "IO"
        const val MAIN_DISPATCHER = "Main"
    }
}