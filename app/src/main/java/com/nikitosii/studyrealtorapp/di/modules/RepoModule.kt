package com.studyrealtorapp.di.modules

import android.content.Context
import com.nikitosii.studyrealtorapp.core.source.connectivity.ConnectivityProvider
import com.nikitosii.studyrealtorapp.core.source.db.dao.SalePropertiesSearchDao
import com.nikitosii.studyrealtorapp.core.source.local.LocalStorage
import com.nikitosii.studyrealtorapp.core.source.net.NetworkErrorHandler
import com.nikitosii.studyrealtorapp.core.source.net.api.PropertiesApi
import com.nikitosii.studyrealtorapp.core.source.net.api.image.ImageApi
import com.nikitosii.studyrealtorapp.core.source.repository.ImageRepo
import com.nikitosii.studyrealtorapp.core.source.repository.PropertiesRepo
import com.nikitosii.studyrealtorapp.core.source.repository.TokenRepo
import com.nikitosii.studyrealtorapp.core.source.repository.base.ChannelRecreateObserver
import com.nikitosii.studyrealtorapp.core.source.repository.impl.ImageRepoImpl
import com.nikitosii.studyrealtorapp.core.source.repository.impl.PropertiesRepoImpl
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
        context: Context
    ): PropertiesRepo = PropertiesRepoImpl(
        api,
        dao,
        io,
        channelRecreateObserver,
        connectivityProvider,
        networkErrorHandler,
        context
    )

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
}