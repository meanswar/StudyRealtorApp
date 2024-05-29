package com.nikitosii.studyrealtorapp.core.source.repository.impl

import android.content.Context
import com.nikitosii.studyrealtorapp.core.source.connectivity.ConnectivityProvider
import com.nikitosii.studyrealtorapp.core.source.db.dao.PropertyDao
import com.nikitosii.studyrealtorapp.core.source.db.entity.PropertyEntity
import com.nikitosii.studyrealtorapp.core.source.local.model.Property
import com.nikitosii.studyrealtorapp.core.source.local.model.property_details.PropertyDetails
import com.nikitosii.studyrealtorapp.core.source.local.model.request.SearchRequest
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
    private val dao: PropertyDao,
    networkErrorHandler: NetworkErrorHandler,
    val context: Context,
    io: CoroutineDispatcher,
    connectivityProvider: ConnectivityProvider,
    recreateObserver: ChannelRecreateObserver
) : BaseRepo(networkErrorHandler), PropertiesRepo {

    override suspend fun getPropertiesForSale(
        data: SearchRequest,
        page: Int?
    ): List<Property> = runWithErrorHandler {
        val houses = data.houses.map { it.apiType }.joinToString(",") { it }
        api.getPropertiesForSale(
            data.address,
            houses.ifEmpty { null },
            data.priceMin,
            data.priceMax,
            data.bedsMin,
            data.bedsMax,
            data.bathsMin,
            data.bathsMax,
            data.sqftMin,
            data.sqftMax,
            page,
            data.sort?.type
        )
            .homeSearch
            .results
            .map { Property.from(it) }
    }

    private val channel = repoChannel(io, connectivityProvider, recreateObserver) {
        storageConfig { get = { dao.getProperties().map { Property.from(it) } } }
    }

    override suspend fun saveProperties(properties: List<Property>) {
        dao.insertProperties(properties.map { PropertyEntity.from(it) })
    }

    override suspend fun updateProperty(property: Property) {
        val updated = PropertyEntity.from(property)
        dao.insertProperty(updated)
    }

    override suspend fun getLocalFavoriteProperties(ids: List<String>): List<Property> =
        dao.getFavoriteProperties(ids).map { Property.from(it) }

    override suspend fun getFavoritePropertiesIds(ids: List<String>): List<String> =
        dao.getFavoriteProperties(ids).map { Property.from(it) }.map { it.propertyId }


    override suspend fun getLocalProperties(ids: List<String>): List<Property> =
        dao.getLocalProperties(ids).map { Property.from(it) }

    override suspend fun getLocalProperty(id: String): Property =
        Property.from(dao.getLocalProperty(id))


    override fun getAllLocalProperties(): Flow<List<Property>> = channel.value.flow

    override suspend fun refreshProperties() = channel.value.refreshOnlyLocal()

    override suspend fun removeData() { dao.deleteAllProperties() }

    override suspend fun getPropertyDetails(id: String): PropertyDetails = runWithErrorHandler {
        PropertyDetails.from(api.getPropertyDetails(id).result)
    }

    override suspend fun getPropertiesForRent(data: SearchRequest, page: Int?): List<Property> =
        runWithErrorHandler {
            val houses = data.houses.map { it.apiType }.joinToString(",") { it }
            api.getPropertiesForRent(
                data.address,
                houses.ifEmpty { null },
                data.priceMin,
                data.priceMax,
                data.bedsMin,
                data.bedsMax,
                data.bathsMin,
                data.bathsMax,
                data.sqftMin,
                data.sqftMax,
                data.cats,
                data.dogs,
                page,
                data.sort?.type
            ).results.map { Property.from(it) }
        }

    // Method to read local json data for emulating network data
//    private fun getLocalRentProperty(): PropertyDetails {
//       val data = JsonReader.readJson<BaseSinglePropertyResponseApi>(context, R.raw.rent)?.result
//        return PropertyDetails.from(data)
//    }
}