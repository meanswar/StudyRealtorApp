package com.nikitosii.studyrealtorapp.domain.usecase.properties

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nikitosii.studyrealtorapp.core.domain.useCase.properties.GetLocalPropertiesUseCase
import com.nikitosii.studyrealtorapp.core.source.db.dao.PropertyDao
import com.nikitosii.studyrealtorapp.core.source.local.model.Property
import com.nikitosii.studyrealtorapp.di.DaggerTestAppComponent
import com.nikitosii.studyrealtorapp.domain.usecase.base.BaseUseCaseTest
import com.nikitosii.studyrealtorapp.util.PropertyTestUtils
import com.nikitosii.studyrealtorapp.util.RequestDataTestUtils
import com.nikitosii.studyrealtorapp.util.TestConstants.ID_INVALID
import com.nikitosii.studyrealtorapp.util.TestConstants.ID_VALID
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.internal.runners.JUnit4ClassRunner
import org.junit.runner.RunWith
import javax.inject.Inject

@ExperimentalCoroutinesApi
@RunWith(JUnit4ClassRunner::class)
class GetLocalPropertiesUseCaseTest : BaseUseCaseTest<GetLocalPropertiesUseCase>() {

    @Inject
    lateinit var dao: PropertyDao

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    override fun setup() {
        DaggerTestAppComponent.builder()
            .build()
            .inject(this)
        dao.deleteAllProperties()
        dao.insertProperties(PropertyTestUtils.getExpectedProperties())
    }

    @After
    fun tearDown() {
        dao.deleteAllProperties()
    }

    @Test
    fun `get local properties valid REQUEST_ID test`() = runTest {
        val params = GetLocalPropertiesUseCase.Params.from(ID_VALID)
        val filter = RequestDataTestUtils.getPropertiesIds()
        val result = useCase.execute(params)
        val expected = PropertyTestUtils.getExpectedProperties()
            .filter { it.propertyId in filter }
            .map { Property.from(it) }

        assertEquals(PROPERTIES_EXPECTED_SIZE, result.size)
        assertEquals(expected, result)
    }

    @Test
    fun `get local properties invalid REQUEST_ID test`() = runTest {
        val params = GetLocalPropertiesUseCase.Params.from(ID_INVALID)
        val result = useCase.execute(params)
        assert(result.isEmpty())
    }

    companion object {
        private const val PROPERTIES_EXPECTED_SIZE = 3
    }
}