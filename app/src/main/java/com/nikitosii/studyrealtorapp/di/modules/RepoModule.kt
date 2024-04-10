package com.studyrealtorapp.di.modules

import com.nikitosii.studyrealtorapp.core.source.local.LocalStorage
import com.nikitosii.studyrealtorapp.core.source.net.NetworkErrorHandler
import com.nikitosii.studyrealtorapp.core.source.net.api.PropertiesApi
import com.nikitosii.studyrealtorapp.core.source.repository.PropertiesRepo
import com.nikitosii.studyrealtorapp.core.source.repository.TokenRepo
import com.nikitosii.studyrealtorapp.core.source.repository.impl.PropertiesRepoImpl
import com.nikitosii.studyrealtorapp.core.source.repository.impl.TokenRepoImpl
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

    @Provides
    @Singleton
    internal fun provideTokenRepo(
        localStorage: LocalStorage
    ): TokenRepo = TokenRepoImpl(localStorage)
}