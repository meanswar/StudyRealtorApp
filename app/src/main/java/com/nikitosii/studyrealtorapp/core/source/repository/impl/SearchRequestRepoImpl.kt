package com.nikitosii.studyrealtorapp.core.source.repository.impl

import com.nikitosii.studyrealtorapp.core.source.connectivity.ConnectivityProvider
import com.nikitosii.studyrealtorapp.core.source.db.dao.SearchRequestDao
import com.nikitosii.studyrealtorapp.core.source.local.model.request.RequestType
import com.nikitosii.studyrealtorapp.core.source.local.model.request.SearchRequest
import com.nikitosii.studyrealtorapp.core.source.net.NetworkErrorHandler
import com.nikitosii.studyrealtorapp.core.source.repository.SearchRequestRepo
import com.nikitosii.studyrealtorapp.core.source.repository.base.BaseRepo
import com.nikitosii.studyrealtorapp.core.source.repository.base.ChannelRecreateObserver
import com.nikitosii.studyrealtorapp.core.source.repository.base.repoChannel
import com.nikitosii.studyrealtorapp.util.Flow
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import javax.inject.Inject

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
class SearchRequestRepoImpl @Inject constructor(
    private val dao: SearchRequestDao,
    io: CoroutineDispatcher,
    connectivityProvider: ConnectivityProvider,
    recreateObserver: ChannelRecreateObserver,
    networkErrorHandler: NetworkErrorHandler
) : SearchRequestRepo, BaseRepo(networkErrorHandler) {

    val channelSaleSearchRequests = repoChannel(io, connectivityProvider, recreateObserver) {
        storageConfig {
            get = { dao.getRecentSearchRequests(RequestType.SALE).map { SearchRequest.from(it) } }
        }
    }

    val channelRentSearchRequests = repoChannel(io, connectivityProvider, recreateObserver) {
        storageConfig {
            get = { dao.getRecentSearchRequests(RequestType.RENT).map { SearchRequest.from(it) } }
        }
    }

    val channelSearchRequests = repoChannel(io, connectivityProvider, recreateObserver) {
        storageConfig { get = { dao.getAllSearchRequests().map { SearchRequest.from(it) } } }
    }

    override suspend fun saveSearchRequest(request: SearchRequest): SearchRequest {
        val id = dao.insert(SearchRequest.toEntity(request))
        return request.copy(id = id.toInt())
    }

    override suspend fun removeSearchRequest(id: Int) {
        dao.remove(id)
    }

    override fun getRecentSearchRequests(type: RequestType): Flow<List<SearchRequest>> =
        if (type == RequestType.RENT) channelRentSearchRequests.value.flow else channelSaleSearchRequests.value.flow

    override fun getLocalRequests(): Flow<List<SearchRequest>> = channelSearchRequests.value.flow

    override suspend fun removeData() { dao.removeAll() }

    override suspend fun refreshSearchRequests() = channelSearchRequests.value.refreshOnlyLocal()

    override suspend fun refreshRecentSearchRequests(type: RequestType) {
        if (type == RequestType.RENT) channelRentSearchRequests.value.refresh()
        else channelSaleSearchRequests.value.refresh()
    }

    override suspend fun updateSearchRequest(request: SearchRequest) {
        val entity = SearchRequest.toEntity(request)
        dao.insert(entity)
    }
}