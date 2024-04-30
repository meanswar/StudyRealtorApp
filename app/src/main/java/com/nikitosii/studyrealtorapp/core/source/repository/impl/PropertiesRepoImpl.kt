package com.nikitosii.studyrealtorapp.core.source.repository.impl

import com.nikitosii.studyrealtorapp.core.source.connectivity.ConnectivityProvider
import com.nikitosii.studyrealtorapp.core.source.db.dao.SalePropertiesSearchDao
import com.nikitosii.studyrealtorapp.core.source.db.entity.PropertyEntity
import com.nikitosii.studyrealtorapp.core.source.db.entity.SalePropertiesSearchEntity
import com.nikitosii.studyrealtorapp.core.source.local.model.Property
import com.nikitosii.studyrealtorapp.core.source.local.model.property_details.PropertyDetails
import com.nikitosii.studyrealtorapp.core.source.local.model.request.SalesRequest
import com.nikitosii.studyrealtorapp.core.source.net.NetworkErrorHandler
import com.nikitosii.studyrealtorapp.core.source.net.api.PropertiesApi
import com.nikitosii.studyrealtorapp.core.source.repository.PropertiesRepo
import com.nikitosii.studyrealtorapp.core.source.repository.base.BaseRepo
import com.nikitosii.studyrealtorapp.core.source.repository.base.ChannelRecreateObserver
import com.nikitosii.studyrealtorapp.core.source.repository.base.repoChannel
import com.nikitosii.studyrealtorapp.util.Flow
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import javax.inject.Inject

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
class PropertiesRepoImpl @Inject constructor(
    private val api: PropertiesApi,
    private val dao: SalePropertiesSearchDao,
    io: CoroutineDispatcher,
    channelRecreateObserver: ChannelRecreateObserver,
    connectivityProvider: ConnectivityProvider,
    networkErrorHandler: NetworkErrorHandler
) : BaseRepo(networkErrorHandler), PropertiesRepo {

    private val channel = repoChannel<List<SalesRequest>>(
        io,
        connectivityProvider,
        channelRecreateObserver
    ) {
        storageConfig {
            get = { dao.getLastSaleRequests() }
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
        dao.insert(salesData)
        result
    }


    override fun getLastSaleRequestsHistory(): Flow<List<SalesRequest>> = channel.value.flow

    override suspend fun getSaleRequestsHistory(): List<SalesRequest> = runWithErrorHandler {
        dao.getSaleRequests()
    }

    override suspend fun updateRequestHistory() = runWithErrorHandler { channel.value.refreshOnlyLocal() }

    override suspend fun getByQuery(query: SalesRequest): List<Property>  = runWithErrorHandler {
        dao.getByQuery(query).result.map { Property.from(it) }
    }

    override suspend fun getPropertyDetails(id: String): PropertyDetails = runWithErrorHandler{
        PropertyDetails.from(api.getPropertyDetails(id).result)
    }
}