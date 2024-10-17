package com.nikitosii.studyrealtorapp.domain.flow

import androidx.lifecycle.Observer
import com.nikitosii.studyrealtorapp.core.domain.useCase.properties.GetAllLocalPropertiesUseCase
import com.nikitosii.studyrealtorapp.core.domain.useCase.properties.RemovePropertyUseCase
import com.nikitosii.studyrealtorapp.core.domain.useCase.properties.UpdatePropertyUseCase
import com.nikitosii.studyrealtorapp.core.source.channel.Status
import com.nikitosii.studyrealtorapp.core.source.local.model.Property
import com.nikitosii.studyrealtorapp.flow.profile.properties.ProfilePropertiesViewModel
import com.nikitosii.studyrealtorapp.util.PropertyTestUtils
import com.nikitosii.studyrealtorapp.util.TestConstants.THREAD_SLEEP_TIME
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class ProfilePropertiesViewModelTests : BaseViewModelTest<ProfilePropertiesViewModel>() {
    @Mock
    private lateinit var getLocalPropertiesUseCase: GetAllLocalPropertiesUseCase

    @Mock
    private lateinit var removePropertyUseCase: RemovePropertyUseCase

    @Mock
    private lateinit var updatePropertyUseCase: UpdatePropertyUseCase


    private val flow = MutableSharedFlow<Status<List<Property>>>()
    private lateinit var observer: Observer<List<Property>>

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        Dispatchers.setMain(testDispatcher)
        `when`(getLocalPropertiesUseCase.execute()).thenReturn(flow)
        viewModel = ProfilePropertiesViewModel(
            getLocalPropertiesUseCase,
            removePropertyUseCase,
            updatePropertyUseCase,
            testDispatcher,
            Dispatchers.Main
        )

        observer = Observer {}
        viewModel.properties.observeForever(observer)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        viewModel.properties.removeObserver(observer)
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `update property test`() = runBlockingTest {
        val property = PropertyTestUtils.getExpectedLocalProperty()
        val updatedProperty = property.copy(favorite = !property.favorite)
        val params = UpdatePropertyUseCase.Params.create(updatedProperty)

        viewModel.properties.postValue(PropertyTestUtils.getExpectedLocalProperties())

        `when`(updatePropertyUseCase.execute(params)).thenReturn(Unit)

        viewModel.updateProperty(property)


        verify(updatePropertyUseCase).execute(params)
        verify(getLocalPropertiesUseCase).execute()
    }

    @Test
    fun `remove property test`() = runBlockingTest {
        val removeProperty = PropertyTestUtils.getExpectedLocalProperty()
        val params = RemovePropertyUseCase.Params(removeProperty.propertyId)

        viewModel.properties.postValue(PropertyTestUtils.getExpectedLocalProperties())
        `when`(removePropertyUseCase.execute(params)).thenReturn(Unit)

        viewModel.removePropertyById(removeProperty.propertyId)

        verify(removePropertyUseCase).execute(params)
        verify(getLocalPropertiesUseCase).execute()
    }
}