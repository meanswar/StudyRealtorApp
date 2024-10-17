package com.nikitosii.studyrealtorapp.domain.usecase.properties

import com.nikitosii.studyrealtorapp.MainCoroutineScopeRule
import com.nikitosii.studyrealtorapp.core.domain.useCase.properties.GetLocalPropertyUseCase
import com.nikitosii.studyrealtorapp.core.source.db.dao.PropertyDao
import com.nikitosii.studyrealtorapp.core.source.local.model.Property
import com.nikitosii.studyrealtorapp.di.DaggerTestAppComponent
import com.nikitosii.studyrealtorapp.domain.usecase.base.BaseUseCaseTest
import com.nikitosii.studyrealtorapp.util.PropertyTestUtils
import com.nikitosii.studyrealtorapp.util.TestConstants
import com.nikitosii.studyrealtorapp.util.TestConstants.ID_INVALID_TEXT
import com.nikitosii.studyrealtorapp.util.TestConstants.ID_VALID_TEXT
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.BlockJUnit4ClassRunner
import org.junit.runners.JUnit4
import org.mockito.junit.MockitoJUnitRunner
import javax.inject.Inject

@ExperimentalCoroutinesApi
@RunWith(BlockJUnit4ClassRunner::class)
class GetLocalPropertyUseCaseTest : BaseUseCaseTest<GetLocalPropertyUseCase>() {

    @get:Rule
    var rule = MainCoroutineScopeRule()

    @Inject
    lateinit var dao: PropertyDao

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
    fun `get local property valid test`() = runTest {
        val expected = Property.from(PropertyTestUtils.getExpectedProperty(ID_VALID_TEXT))
        val params = GetLocalPropertyUseCase.Params.create(ID_VALID_TEXT)
        val result = useCase.execute(params)
        assertEquals(expected, result)
    }

    @Test
    fun `get local property invalid test`() = runTest {
        val params = GetLocalPropertyUseCase.Params.create(ID_INVALID_TEXT)
        try {
            useCase.execute(params)
            assert(false)
        } catch (e: Exception) {
            assertEquals(TestConstants.EXCEPTION_ITEM_NOT_EXISTS, e.message)
        }
    }
}