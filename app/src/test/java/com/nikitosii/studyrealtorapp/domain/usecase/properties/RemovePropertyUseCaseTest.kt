package com.nikitosii.studyrealtorapp.domain.usecase.properties

import com.nikitosii.studyrealtorapp.core.domain.useCase.properties.RemovePropertyUseCase
import com.nikitosii.studyrealtorapp.core.source.db.dao.PropertyDao
import com.nikitosii.studyrealtorapp.core.source.local.model.Property
import com.nikitosii.studyrealtorapp.di.DaggerTestAppComponent
import com.nikitosii.studyrealtorapp.domain.usecase.base.BaseUseCaseTest
import com.nikitosii.studyrealtorapp.util.PropertyTestUtils
import com.nikitosii.studyrealtorapp.util.TestConstants.ID_INVALID_TEXT
import com.nikitosii.studyrealtorapp.util.TestConstants.ID_VALID_FOR_REMOVING
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.internal.runners.JUnit4ClassRunner
import org.junit.runner.RunWith
import javax.inject.Inject

@ExperimentalCoroutinesApi
@RunWith(JUnit4ClassRunner::class)
class RemovePropertyUseCaseTest : BaseUseCaseTest<RemovePropertyUseCase>() {

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
    fun `remove property valid PROPERTY_ID test`() = runTest {
        val params = RemovePropertyUseCase.Params.create(ID_VALID_FOR_REMOVING)

        assertEquals(
            PROPERTIES_EXPECTED_EXISTED_PROPERTY,
            dao.getProperties().filter { it.propertyId == ID_VALID_FOR_REMOVING }.size
        )

        useCase.execute(params)

        assertEquals(
            PROPERTIES_EXPECTED_NOT_EXISTED_PROPERTY,
            dao.getProperties().filter { it.propertyId == ID_VALID_FOR_REMOVING }.size
        )
    }

    @Test
    fun `remove property invalid PROPERTY_ID test`() = runTest {
        val params = RemovePropertyUseCase.Params.create(ID_INVALID_TEXT)
        assertThat(dao.getProperties().none { it.propertyId == ID_INVALID_TEXT }).isEqualTo(true)
        useCase.execute(params)
        assertThat(dao.getProperties().none { it.propertyId == ID_INVALID_TEXT }).isEqualTo(true)
    }

    companion object {
        private const val PROPERTIES_EXPECTED_EXISTED_PROPERTY = 1
        private const val PROPERTIES_EXPECTED_NOT_EXISTED_PROPERTY = 0
    }
}