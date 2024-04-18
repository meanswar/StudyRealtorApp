package com.nikitosii.studyrealtorapp.core.source.repository.impl

import com.nikitosii.studyrealtorapp.core.source.connectivity.ConnectivityProvider
import com.nikitosii.studyrealtorapp.core.source.db.dao.SalePropertiesSearchDao
import com.nikitosii.studyrealtorapp.core.source.db.dao.SalePropertyDao
import com.nikitosii.studyrealtorapp.core.source.db.entity.PropertyEntity
import com.nikitosii.studyrealtorapp.core.source.db.entity.SalePropertiesSearchEntity
import com.nikitosii.studyrealtorapp.core.source.local.model.Property
import com.nikitosii.studyrealtorapp.core.source.local.model.request.SalesRequest
import com.nikitosii.studyrealtorapp.core.source.net.NetworkErrorHandler
import com.nikitosii.studyrealtorapp.core.source.net.api.PropertiesApi
import com.nikitosii.studyrealtorapp.core.source.repository.SalePropertiesRepo
import com.nikitosii.studyrealtorapp.core.source.repository.base.BaseRepo
import com.nikitosii.studyrealtorapp.core.source.repository.base.ChannelRecreateObserver
import com.nikitosii.studyrealtorapp.core.source.repository.base.repoChannel
import com.nikitosii.studyrealtorapp.util.Flow
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import javax.inject.Inject

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
class SalePropertiesRepoImpl @Inject constructor(
    private val api: PropertiesApi,
    private val dao: SalePropertyDao,
    private val salePropertiesDao: SalePropertiesSearchDao,
    io: CoroutineDispatcher,
    channelRecreateObserver: ChannelRecreateObserver,
    connectivityProvider: ConnectivityProvider,
    networkErrorHandler: NetworkErrorHandler
) : BaseRepo(networkErrorHandler), SalePropertiesRepo {

    private val channel = repoChannel<List<SalesRequest>>(
        io,
        connectivityProvider,
        channelRecreateObserver
    ) {
        storageConfig {
            get = { salePropertiesDao.getSaleRequests() }
        }
    }

    override suspend fun getPropertiesForSale(
        data: SalesRequest,
        page: Int?,
        sort: String?
    ): List<Property> = runWithErrorHandler {
        val houses = data.houses?.joinToString(",") { it }
        val result = api.getPropertiesForSale(
            data.address,
            if (houses.isNullOrEmpty()) null else houses,
            data.priceMin,
            data.priceMax,
            data.bedsMin,
            data.bedsMax,
            data.bathsMin,
            data.bathsMax,
            data.sqftMin,
            data.sqftMax,
            page,
            sort
        )
            .homeSearch
            .results
            .map { Property.from(it) }
        val salesData =
            SalePropertiesSearchEntity.from(data, result.map { PropertyEntity.from(it) })
        salePropertiesDao.insert(salesData)
        result
    }


    override fun getSaleRequestsHistory(): Flow<List<SalesRequest>> = channel.value.flow

    override suspend fun updateRequestHistory() = runWithErrorHandler {
        channel.value.refresh()
    }
}