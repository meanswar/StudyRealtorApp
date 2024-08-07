package com.nikitosii.studyrealtorapp.domain.flow

import androidx.lifecycle.Observer
import com.nikitosii.studyrealtorapp.core.domain.Status
import com.nikitosii.studyrealtorapp.core.domain.WorkResult
import com.nikitosii.studyrealtorapp.core.domain.useCase.properties.GetPropertyDetailsUseCase
import com.nikitosii.studyrealtorapp.core.domain.useCase.properties.UpdatePropertyUseCase
import com.nikitosii.studyrealtorapp.core.source.local.model.Property
import com.nikitosii.studyrealtorapp.core.source.local.model.property_details.PropertyDetails
import com.nikitosii.studyrealtorapp.flow.details.PropertyDetailsViewModel
import com.nikitosii.studyrealtorapp.util.PropertyTestUtils
import com.nikitosii.studyrealtorapp.util.TestConstants
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class PropertyDetailsViewModelTest : BaseViewModelTest<PropertyDetailsViewModel>() {
    @Mock
    private lateinit var getPropertyDetailsUseCase: GetPropertyDetailsUseCase

    @Mock
    private lateinit var updatePropertyUseCase: UpdatePropertyUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = PropertyDetailsViewModel(
            getPropertyDetailsUseCase,
            updatePropertyUseCase,
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
    fun `get property details live data valid`() = runTest {
        val params = GetPropertyDetailsUseCase.Params.create(TestConstants.ID_VALID_TEXT)
        val expected = PropertyDetails(/*...*/)
        val expectedStatus = Status.SUCCESS

        `when`(getPropertyDetailsUseCase.execute(params)).thenReturn(expected)

        val observer = Observer<WorkResult<PropertyDetails>> {}
        try {
            viewModel.property.observeForever(observer)

            viewModel.getPropertyDetails(params.id)

            verify(getPropertyDetailsUseCase).execute(params)
            assertEquals(expectedStatus, viewModel.property.value?.status)
            assertEquals(expected, viewModel.property.value?.data)
        } finally {
            viewModel.property.removeObserver(observer)
        }
    }

    @Test
    fun `get property details live data Invalid`() = runTest {
        val params = GetPropertyDetailsUseCase.Params.create(TestConstants.ID_INVALID_TEXT)
        val expected = Exception(TestConstants.EXCEPTION_WRONG_PARAMS)
        val expectedStatus = Status.ERROR

        `when`(getPropertyDetailsUseCase.execute(params)).thenAnswer { expected }

        val observer = Observer<WorkResult<PropertyDetails>> {}
        try {
            viewModel.property.observeForever(observer)

            viewModel.getPropertyDetails(params.id)
        } catch (e: Exception) {
            verify(getPropertyDetailsUseCase).execute(params)
            assertEquals(expected.message, e.message)
            assertEquals(expectedStatus, viewModel.property.value?.status)
        } finally {
            viewModel.property.removeObserver(observer)
        }
    }

    @Test
    fun `updateMapStatus updates LiveData`() {
        val isMapReady = true
        val observer = Observer<Boolean> {}
        try {
            viewModel.isMapReady.observeForever(observer)

            viewModel.updateMapStatus(isMapReady)

            assertEquals(isMapReady, viewModel.isMapReady.value)
        } finally {
            viewModel.isMapReady.removeObserver(observer)
        }
    }

    @Test
    fun `toogle property favorite status`() = runTest {
        val property = Property.from(PropertyTestUtils.getExpectedProperty())
        val updatedProperty = property.copy(favorite = TestConstants.BOOLEAN_FALSE)
        val params = UpdatePropertyUseCase.Params.create(updatedProperty)

        `when`(updatePropertyUseCase.execute(params)).thenReturn(Unit)
        viewModel.setLocalProperty(property)

        val observer = Observer<WorkResult<Unit>> {}
        try {
            viewModel.updateStatus.observeForever(observer)

            viewModel.onFavoriteClick()

            assert(viewModel.localProperty.value == updatedProperty)
            verify(updatePropertyUseCase).execute(params)
        } finally {
            viewModel.updateStatus.removeObserver(observer)
        }
    }
}