package com.nikitosii.studyrealtorapp.di.modules

import android.content.Context
import com.nikitosii.studyrealtorapp.core.source.db.dao.AgentDao
import com.nikitosii.studyrealtorapp.core.source.db.dao.PropertyDao
import com.nikitosii.studyrealtorapp.core.source.db.dao.RequestDataDao
import com.nikitosii.studyrealtorapp.core.source.db.dao.SearchRequestDao
import com.nikitosii.studyrealtorapp.core.source.local.LocalStorage
import com.nikitosii.studyrealtorapp.core.source.net.NetworkErrorHandler
import com.nikitosii.studyrealtorapp.core.source.net.api.AgentsApi
import com.nikitosii.studyrealtorapp.core.source.net.api.PropertiesApi
import com.nikitosii.studyrealtorapp.core.source.net.api.image.ImageApi
import com.nikitosii.studyrealtorapp.core.source.repository.AgentsRepo
import com.nikitosii.studyrealtorapp.core.source.repository.ImageRepo
import com.nikitosii.studyrealtorapp.core.source.repository.PropertiesRepo
import com.nikitosii.studyrealtorapp.core.source.repository.RequestDataRepo
import com.nikitosii.studyrealtorapp.core.source.repository.SearchRequestRepo
import com.nikitosii.studyrealtorapp.core.source.repository.TokenRepo
import com.nikitosii.studyrealtorapp.core.source.repository.impl.AgentsRepoImpl
import com.nikitosii.studyrealtorapp.core.source.repository.impl.ImageRepoImpl
import com.nikitosii.studyrealtorapp.core.source.repository.impl.PropertiesRepoImpl
import com.nikitosii.studyrealtorapp.core.source.repository.impl.RequestDataRepoImpl
import com.nikitosii.studyrealtorapp.core.source.repository.impl.SearchRequestRepoImpl
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
        dao: PropertyDao,
        networkErrorHandler: NetworkErrorHandler,
        context: Context
    ): PropertiesRepo = PropertiesRepoImpl(api, dao, networkErrorHandler, context)

    @Provides
    @Singleton
    internal fun provideSearchRequestRepo(dao: SearchRequestDao): SearchRequestRepo =
        SearchRequestRepoImpl(dao)

    @Provides
    @Singleton
    internal fun provideRequestDataRepo(
        dao: RequestDataDao
    ): RequestDataRepo = RequestDataRepoImpl(dao)

    @Provides
    @Singleton
    internal fun provideTokenRepo(
        localStorage: LocalStorage
    ): TokenRepo = TokenRepoImpl(localStorage)

    @Provides
    @Singleton
    internal fun provideImageRepo(
        api: ImageApi,
        networkErrorHandler: NetworkErrorHandler
    ): ImageRepo = ImageRepoImpl(api, networkErrorHandler)

    @Provides
    @Singleton
    internal fun provideAgentsRepo(
        api: AgentsApi,
        dao: AgentDao,
        networkErrorHandler: NetworkErrorHandler
    ): AgentsRepo = AgentsRepoImpl(api, dao, networkErrorHandler)
}