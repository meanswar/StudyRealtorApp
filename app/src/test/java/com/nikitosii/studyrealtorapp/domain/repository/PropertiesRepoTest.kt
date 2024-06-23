package com.nikitosii.studyrealtorapp.domain.repository

import android.content.Context
import com.nikitosii.studyrealtorapp.MainCoroutineScopeRule
import com.nikitosii.studyrealtorapp.core.domain.repository.PropertiesRepo
import com.nikitosii.studyrealtorapp.core.domain.repository.base.ChannelRecreateObserver
import com.nikitosii.studyrealtorapp.core.domain.repository.impl.PropertiesRepoImpl
import com.nikitosii.studyrealtorapp.core.source.connectivity.ConnectivityProvider
import com.nikitosii.studyrealtorapp.core.source.db.dao.PropertyDao
import com.nikitosii.studyrealtorapp.core.source.local.model.Property
import com.nikitosii.studyrealtorapp.core.source.net.NetworkErrorHandler
import com.nikitosii.studyrealtorapp.core.source.net.api.PropertiesApi
import com.nikitosii.studyrealtorapp.core.source.net.model.base.BaseHomeSearch
import com.nikitosii.studyrealtorapp.core.source.net.model.base.BaseResponse
import com.nikitosii.studyrealtorapp.core.source.net.model.property.PropertyResponseApi
import com.nikitosii.studyrealtorapp.util.PropertyTestUtils
import com.nikitosii.studyrealtorapp.util.SearchRequestTestUtils
import com.nikitosii.studyrealtorapp.util.TestConstants.ANY_DIGITS
import com.nikitosii.studyrealtorapp.util.TestConstants.ANY_LONG
import com.nikitosii.studyrealtorapp.util.TestConstants.ANY_TEXT
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class PropertiesRepoTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainDispatcherRule = MainCoroutineScopeRule()

    private lateinit var repo: PropertiesRepo

    private lateinit var dao: PropertyDao
    private lateinit var api: PropertiesApi
    private lateinit var networkErrorHandler: NetworkErrorHandler
    private lateinit var io: CoroutineDispatcher
    private lateinit var connectivityProvider: ConnectivityProvider
    private lateinit var recreateObserver: ChannelRecreateObserver

    @Before
    fun setup() {
        dao = mockk()
        api = mockk(relaxed = true)
        networkErrorHandler = NetworkErrorHandler(mockk(relaxed = true))
        val context = mockk<Context>()
        io = mockk(relaxed = true)
        connectivityProvider = mockk(relaxed = true)
        recreateObserver = mockk(relaxed = true)

        repo = PropertiesRepoImpl(
            api,
            dao,
            networkErrorHandler,
            context,
            io,
            connectivityProvider,
            recreateObserver
        )
    }

    @Test
    fun `get properties for sale test`() = runTest {
        coEvery {
            api.getPropertiesForSale(
                any(),
                any(),
                any(),
                any(),
                any(),
                any(),
                any(),
                any(),
                any(),
                any(),
                any(),
                any()
            )
        } returns mockk<BaseResponse>(relaxed = true)

        repo.getPropertiesForSale(SearchRequestTestUtils.getExpectedSearchRequest(), ANY_DIGITS)

        coVerify {
            api.getPropertiesForSale(
                location = ANY_TEXT,
                house = null,
                priceMin = ANY_DIGITS,
                priceMax = ANY_DIGITS,
                bedsMin = ANY_DIGITS,
                bedsMax = ANY_DIGITS,
                bathsMin = ANY_DIGITS,
                bathsMax = ANY_DIGITS,
                sqftMin = ANY_DIGITS,
                sqftMax = ANY_DIGITS,
                page = ANY_DIGITS,
                sort = null
            )
        }
    }

    @Test
    fun `get properties for rent test`() = runTest {
        coEvery {
            api.getPropertiesForRent(
                any(),
                any(),
                any(),
                any(),
                any(),
                any(),
                any(),
                any(),
                any(),
                any(),
                any(),
                any(),
                any(),
                any()
            )
        } returns mockk<BaseHomeSearch<PropertyResponseApi>>(relaxed = true)

        repo.getPropertiesForRent(SearchRequestTestUtils.getExpectedSearchRequest(), ANY_DIGITS)

        coVerify {
            api.getPropertiesForRent(
                location = ANY_TEXT,
                house = null,
                priceMin = ANY_DIGITS,
                priceMax = ANY_DIGITS,
                bedsMin = ANY_DIGITS,
                bedsMax = ANY_DIGITS,
                bathsMin = ANY_DIGITS,
                bathsMax = ANY_DIGITS,
                sqftMin = ANY_DIGITS,
                sqftMax = ANY_DIGITS,
                page = ANY_DIGITS,
                sort = null,
                cats = false,
                dogs = false
            )
        }
    }

    @Test
    fun `save properties test`() = runTest {
        val initialData = PropertyTestUtils.getExpectedProperties()
        val request = initialData.map { Property.from(it) }

        coEvery { dao.insertProperties(initialData) } returns mockk(relaxed = true)
        repo.saveProperties(request)

        coVerify { dao.insertProperties(initialData) }
    }

    @Test
    fun `update property test`() = runTest {
        val initialData = PropertyTestUtils.getExpectedProperty()
        val request = Property.from(initialData)

        coEvery { dao.insertProperty(initialData) } returns ANY_LONG
        repo.updateProperty(request)

        coVerify { dao.insertProperty(initialData) }
    }

    @Test
    fun `get local favorite properties test`() = runTest {
        val initialData = PropertyTestUtils.getExpectedProperties()
        val request = initialData.map { it.propertyId }

        coEvery { dao.getFavoriteProperties(request) } returns initialData
        repo.getFavoritePropertiesIds(request)
        coVerify { dao.getFavoriteProperties(request) }
    }

    @Test
    fun `get local favorite properties ids test`() = runTest {
        val initialData = PropertyTestUtils.getExpectedProperties()
        val request = initialData.map { it.propertyId }

        coEvery { dao.getFavoriteProperties(request) } returns mockk(relaxed = true)
        repo.getFavoritePropertiesIds(request)

        coVerify { dao.getFavoriteProperties(request) }
    }

    @Test
    fun `get local properties test`() = runTest {
        val initialData = PropertyTestUtils.getExpectedProperties()
        val request = initialData.map { it.propertyId }

        coEvery { dao.getLocalProperties(request) } returns initialData
        repo.getLocalProperties(request)
        coVerify { dao.getLocalProperties(request) }
    }

    @Test
    fun `get local property test`() = runTest {
        val initialData = PropertyTestUtils.getExpectedProperty()
        val request = initialData.propertyId

        coEvery { dao.getLocalProperty(request) } returns initialData
        repo.getLocalProperty(request)

        coVerify { dao.getLocalProperty(request) }
    }

    @Test
    fun `get property details test`() = runTest {
        val request = PropertyTestUtils.getPropertyId()

        coEvery { api.getPropertyDetails(request) } returns mockk(relaxed = true)
        repo.getPropertyDetails(request)

        coVerify { api.getPropertyDetails(request) }
    }

    @Test
    fun `remove all properties test`() = runTest {
        coEvery { dao.deleteAllProperties() } returns mockk(relaxed = true)
        repo.removeData()

        coVerify { dao.deleteAllProperties() }
    }

    @Test
    fun `remove property test`() = runTest {
        val request = PropertyTestUtils.getPropertyId()

        coEvery { dao.remove(request) } just Runs
        repo.remove(request)

        coVerify { dao.remove(request) }
    }
}