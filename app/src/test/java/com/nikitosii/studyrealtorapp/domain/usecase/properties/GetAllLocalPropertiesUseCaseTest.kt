package com.nikitosii.studyrealtorapp.domain.usecase.properties

import com.nikitosii.studyrealtorapp.MainCoroutineScopeRule
import com.nikitosii.studyrealtorapp.core.domain.useCase.properties.GetAllLocalPropertiesUseCase
import com.nikitosii.studyrealtorapp.core.source.db.dao.PropertyDao
import com.nikitosii.studyrealtorapp.core.source.local.model.Property
import com.nikitosii.studyrealtorapp.di.DaggerTestAppComponent
import com.nikitosii.studyrealtorapp.domain.usecase.base.BaseUseCaseTest
import com.nikitosii.studyrealtorapp.util.PropertyTestUtils
import dev.olog.flow.test.observer.test
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import javax.inject.Inject

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class GetAllLocalPropertiesUseCaseTest : BaseUseCaseTest<GetAllLocalPropertiesUseCase>() {

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
    fun `get all local properties test`() = runTest {
        val expected = PropertyTestUtils.getExpectedProperties().map { Property.from(it) }
        useCase.execute().test(this) {
            assertValue { data ->
                assertEquals(expected, data.obj)
                true
            }
        }
    }
}