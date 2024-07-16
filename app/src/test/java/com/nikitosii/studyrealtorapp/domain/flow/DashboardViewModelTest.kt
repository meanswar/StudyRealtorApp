package com.nikitosii.studyrealtorapp.domain.flow

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nikitosii.studyrealtorapp.core.domain.useCase.profile.GetProfileFlowUseCase
import com.nikitosii.studyrealtorapp.core.domain.useCase.request.GetRecentRentSearchRequestsUseCase
import com.nikitosii.studyrealtorapp.core.domain.useCase.request.GetRecentSaleSearchRequestsUseCase
import com.nikitosii.studyrealtorapp.core.domain.useCase.request.UpdateRequestUseCase
import com.nikitosii.studyrealtorapp.core.source.channel.Status
import com.nikitosii.studyrealtorapp.core.source.local.model.HouseType
import com.nikitosii.studyrealtorapp.core.source.local.model.profile.Profile
import com.nikitosii.studyrealtorapp.core.source.local.model.request.RequestType
import com.nikitosii.studyrealtorapp.core.source.local.model.request.SearchRequest
import com.nikitosii.studyrealtorapp.flow.dashboard.DashboardViewModel
import com.nikitosii.studyrealtorapp.util.TestConstants.ANY_DIGITS
import com.nikitosii.studyrealtorapp.util.TestConstants.ANY_TEXT
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class DashboardViewModelTest {
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val testDispatcher = TestCoroutineDispatcher()

    @Mock
    private lateinit var getRecentSaleRequestsUseCase: GetRecentSaleSearchRequestsUseCase

    @Mock
    private lateinit var getRecentRentRequestsUseCase: GetRecentRentSearchRequestsUseCase

    @Mock
    private lateinit var getProfileFlowUseCase: GetProfileFlowUseCase

    @Mock
    private lateinit var updateSearchRequestUseCase: UpdateRequestUseCase

    private lateinit var viewModel: DashboardViewModel
    private lateinit var recentSaleRequestsFlow: SharedFlow<Status<List<SearchRequest>>>
    private lateinit var recentRentRequestsFlow: SharedFlow<Status<List<SearchRequest>>>
    private lateinit var profileFlow: SharedFlow<Status<Profile>>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        recentSaleRequestsFlow = MutableSharedFlow<Status<List<SearchRequest>>>().asSharedFlow()
        recentRentRequestsFlow = MutableSharedFlow<Status<List<SearchRequest>>>().asSharedFlow()
        profileFlow = MutableSharedFlow<Status<Profile>>().asSharedFlow()

        `when`(getRecentSaleRequestsUseCase.execute()).thenReturn(recentSaleRequestsFlow)
        `when`(getRecentRentRequestsUseCase.execute()).thenReturn(recentRentRequestsFlow)
        `when`(getProfileFlowUseCase.execute()).thenReturn(profileFlow)

        viewModel = DashboardViewModel(
            getRecentSaleRequestsUseCase,
            getRecentRentRequestsUseCase,
            getProfileFlowUseCase,
            updateSearchRequestUseCase,
            Dispatchers.IO,
            testDispatcher
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `checkFilters returns true when filters are set`() {
        val expected = true
        viewModel.addressFilter.value = ANY_TEXT
        viewModel.priceMinFilter.value = ANY_DIGITS
        viewModel.priceMaxFilter.value = ANY_DIGITS

        val result = viewModel.checkFilters()

        assertEquals(expected, result)
    }

    @Test
    fun `checkFilters returns false when no filters are set`() {
        val expected = false
        val result = viewModel.checkFilters()

        assertEquals(expected, result)
    }

    @Test
    fun `buildSaleRequest creates a search request with correct values`() {
        viewModel.addressFilter.value = ANY_TEXT
        viewModel.priceMinFilter.value = ANY_DIGITS
        viewModel.priceMaxFilter.value = ANY_DIGITS
        viewModel.bedsMinFilter.value = ANY_DIGITS
        viewModel.bedsMaxFilter.value = ANY_DIGITS
        viewModel.bathsMinFilter.value = ANY_DIGITS
        viewModel.bathsMaxFilter.value = ANY_DIGITS
        viewModel.sqftMinFilter.value = ANY_DIGITS
        viewModel.sqftMaxFilter.value = ANY_DIGITS

        val request = viewModel.buildSaleRequest()

        assertEquals(ANY_TEXT, request.address)
        assertEquals(ANY_DIGITS, request.priceMin)
        assertEquals(ANY_DIGITS, request.priceMax)
        assertEquals(ANY_DIGITS, request.bedsMin)
        assertEquals(ANY_DIGITS, request.bedsMax)
        assertEquals(ANY_DIGITS, request.bathsMin)
        assertEquals(ANY_DIGITS, request.bathsMax)
        assertEquals(ANY_DIGITS, request.sqftMin)
        assertEquals(ANY_DIGITS, request.sqftMax)
        assertEquals(RequestType.SALE, request.requestType)
    }

    @Test
    fun `setFilterHouse adds house type when not already in the list`() {
        val houseType = HouseType.TOWNHOMES

        val result = viewModel.setFilterHouse(houseType)

        assertTrue(result)
        assertTrue(viewModel.isFilterHousesFilled())
    }

    @Test
    fun `setFilterHouse removes house type when already in the list`() {
        val houseType = HouseType.TOWNHOMES
        viewModel.setFilterHouse(houseType)

        val result = viewModel.setFilterHouse(houseType)

        assertFalse(result)
        assertFalse(viewModel.isFilterHousesFilled())
    }

    @Test
    fun `updateRequest calls updateSearchRequestUseCase with correct params`() = runTest {
        val searchRequest = SearchRequest(
            address = ANY_TEXT,
            houses = listOf(HouseType.TOWNHOMES),
            priceMin = ANY_DIGITS,
            priceMax = ANY_DIGITS,
            bedsMin = ANY_DIGITS,
            bedsMax = ANY_DIGITS,
            bathsMin = ANY_DIGITS,
            bathsMax = ANY_DIGITS,
            sqftMin = ANY_DIGITS,
            sqftMax = ANY_DIGITS,
            requestType = RequestType.SALE
        )
        val params = UpdateRequestUseCase.Params.create(searchRequest)

        `when`(updateSearchRequestUseCase.execute(params)).thenReturn(Unit)

        viewModel.updateRequest(searchRequest)

        verify(updateSearchRequestUseCase).execute(params)
    }
}