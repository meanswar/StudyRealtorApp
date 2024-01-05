package com.studyrealtorapp.di.modules

import android.content.res.Resources
import com.studyrealtorapp.core.source.net.NetworkErrorHandler
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class NetworkErrorModule {
    @Singleton
    @Provides
    internal fun providesErrorHandler(resources: Resources) = NetworkErrorHandler(resources)
}