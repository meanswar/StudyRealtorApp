package com.nikitosii.studyrealtorapp.domain.usecase.properties

import com.nikitosii.studyrealtorapp.util.TestConstants.ID_INVALID_TEXT
import com.nikitosii.studyrealtorapp.util.TestConstants.ID_VALID_FOR_REMOVING
import com.nikitosii.studyrealtorapp.util.TestConstants.ID_VALID_TEXT
import com.nikitosii.studyrealtorapp.core.domain.useCase.properties.RemovePropertyUseCase
import com.nikitosii.studyrealtorapp.core.source.db.dao.PropertyDao
import com.nikitosii.studyrealtorapp.di.DaggerTestAppComponent
import com.nikitosii.studyrealtorapp.domain.usecase.base.BaseUseCaseTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
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
    }

    @Test
    fun `remove property valid PROPERTY_ID test`() = runTest {
        val params = RemovePropertyUseCase.Params.create(ID_VALID_TEXT)
        assertThat(
            dao.getProperties().filter { it.propertyId == ID_VALID_FOR_REMOVING }.size
        ).isEqualTo(1)
        useCase.execute(params)
        assertThat(dao.getProperties().none { it.propertyId == ID_VALID_FOR_REMOVING }).isEqualTo(
            true
        )
    }

    @Test
    fun `remove property invalid PROPERTY_ID test`() = runTest {
        val params = RemovePropertyUseCase.Params.create(ID_INVALID_TEXT)
        assertThat(dao.getProperties().none { it.propertyId == ID_INVALID_TEXT }).isEqualTo(true)
        useCase.execute(params)
        assertThat(dao.getProperties().none { it.propertyId == ID_INVALID_TEXT }).isEqualTo(true)
    }
}