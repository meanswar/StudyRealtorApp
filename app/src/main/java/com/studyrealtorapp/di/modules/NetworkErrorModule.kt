package com.studyrealtorapp.di.modules

import android.content.res.Resources
import com.squareup.moshi.Moshi
import com.studyrealtorapp.core.source.net.NetworkErrorHandler
import com.studyrealtorapp.core.source.net.exceptions.BaseNetworkException
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class NetworkErrorModule {
    @Singleton
    @Provides
    internal fun providesErrorHandler(
        resources: Resources,
        moshi: Moshi,
        @Named("errorMap") errorMap: MutableMap<String, BaseNetworkException>,
        @Named("errorMapFueling") errorMapFueling: MutableMap<String, BaseNetworkException>
    ) = NetworkErrorHandler(resources, moshi, errorMap, errorMapFueling)
}