package com.nikitosii.studyrealtorapp.core.source.repository.impl

import android.annotation.SuppressLint
import android.content.Context
import com.nikitosii.studyrealtorapp.R
import com.nikitosii.studyrealtorapp.core.source.connectivity.ConnectivityProvider
import com.nikitosii.studyrealtorapp.core.source.db.dao.SalePropertiesSearchDao
import com.nikitosii.studyrealtorapp.core.source.db.entity.PropertyEntity
import com.nikitosii.studyrealtorapp.core.source.db.entity.SalePropertiesSearchEntity
import com.nikitosii.studyrealtorapp.core.source.local.model.Property
import com.nikitosii.studyrealtorapp.core.source.local.model.property_details.PropertyDetails
import com.nikitosii.studyrealtorapp.core.source.local.model.request.SalesRequest
import com.nikitosii.studyrealtorapp.core.source.net.NetworkErrorHandler
import com.nikitosii.studyrealtorapp.core.source.net.api.PropertiesApi
import com.nikitosii.studyrealtorapp.core.source.net.model.base.BaseSinglePropertyResponseApi
import com.nikitosii.studyrealtorapp.core.source.repository.PropertiesRepo
import com.nikitosii.studyrealtorapp.core.source.repository.base.BaseRepo
import com.nikitosii.studyrealtorapp.core.source.repository.base.ChannelRecreateObserver
import com.nikitosii.studyrealtorapp.core.source.repository.base.repoChannel
import com.nikitosii.studyrealtorapp.util.Flow
import com.squareup.moshi.Moshi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import java.io.IOException
import java.io.InputStream
import javax.inject.Inject

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
class PropertiesRepoImpl @Inject constructor(
    private val api: PropertiesApi,
    private val dao: SalePropertiesSearchDao,
    io: CoroutineDispatcher,
    channelRecreateObserver: ChannelRecreateObserver,
    connectivityProvider: ConnectivityProvider,
    networkErrorHandler: NetworkErrorHandler,
    val context: Context
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
//        PropertyDetails.from(api.getPropertyDetails(id).result)
        getLocalData()
    }

    @SuppressLint("CheckResult")
    private fun getLocalData(): PropertyDetails {
        try {
            val data = context.resources.openRawResource(R.raw.buy)
            val size = data.available()
            val buffer = ByteArray(size)
            data.read(buffer)
            data.close()
            val jsonResponse = String(buffer)
            val result = Moshi.Builder().build().adapter(BaseSinglePropertyResponseApi::class.java).fromJson(jsonResponse)
            return PropertyDetails.from(result?.result)
        } catch (e: IOException) {
            e.printStackTrace();
            return PropertyDetails()
        }
    }
}