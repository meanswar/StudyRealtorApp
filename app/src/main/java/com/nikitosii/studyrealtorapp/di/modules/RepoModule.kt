package com.studyrealtorapp.di.modules

import com.nikitosii.studyrealtorapp.core.source.connectivity.ConnectivityProvider
import com.nikitosii.studyrealtorapp.core.source.db.dao.SalePropertiesSearchDao
import com.nikitosii.studyrealtorapp.core.source.db.dao.SalePropertyDao
import com.nikitosii.studyrealtorapp.core.source.local.LocalStorage
import com.nikitosii.studyrealtorapp.core.source.net.NetworkErrorHandler
import com.nikitosii.studyrealtorapp.core.source.net.api.PropertiesApi
import com.nikitosii.studyrealtorapp.core.source.repository.SalePropertiesRepo
import com.nikitosii.studyrealtorapp.core.source.repository.TokenRepo
import com.nikitosii.studyrealtorapp.core.source.repository.base.ChannelRecreateObserver
import com.nikitosii.studyrealtorapp.core.source.repository.impl.SalePropertiesRepoImpl
import com.nikitosii.studyrealtorapp.core.source.repository.impl.TokenRepoImpl
import com.nikitosii.studyrealtorapp.di.modules.AppModule
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Named
import javax.inject.Singleton

@Module
object RepoModule {

    @Provides
    @Singleton
    internal fun providePropertiesRepo(
        api: PropertiesApi,
        dao: SalePropertiesSearchDao,
        networkErrorHandler: NetworkErrorHandler,
        @Named(AppModule.IO_DISPATCHER) io: CoroutineDispatcher,
        channelRecreateObserver: ChannelRecreateObserver,
        connectivityProvider: ConnectivityProvider,
    ): SalePropertiesRepo = SalePropertiesRepoImpl(
        api,
        dao,
        io,
        channelRecreateObserver,
        connectivityProvider,
        networkErrorHandler
    )

    @Provides
    @Singleton
    internal fun provideTokenRepo(
        localStorage: LocalStorage
    ): TokenRepo = TokenRepoImpl(localStorage)
}