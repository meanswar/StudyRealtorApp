package com.studyrealtorapp.di.modules

import com.studyrealtorapp.core.source.net.NetworkErrorHandler
import com.studyrealtorapp.core.source.net.api.PropertiesApi
import com.studyrealtorapp.core.source.repository.PropertiesRepo
import com.studyrealtorapp.core.source.repository.impl.PropertiesRepoImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object RepoModule {

    @Provides
    @Singleton
    internal fun providePropertiesRepo(
        api: PropertiesApi,
        networkErrorHandler: NetworkErrorHandler
    ): PropertiesRepo = PropertiesRepoImpl(api, networkErrorHandler)
}