package com.nikitosii.studyrealtorapp.domain.usecase.properties

import com.nikitosii.studyrealtorapp.core.domain.useCase.properties.UpdatePropertyUseCase
import com.nikitosii.studyrealtorapp.core.source.db.dao.PropertyDao
import com.nikitosii.studyrealtorapp.core.source.local.model.Property
import com.nikitosii.studyrealtorapp.di.DaggerTestAppComponent
import com.nikitosii.studyrealtorapp.domain.usecase.base.BaseUseCaseTest
import com.nikitosii.studyrealtorapp.util.PropertyTestUtils
import com.nikitosii.studyrealtorapp.util.TestConstants
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.internal.runners.JUnit4ClassRunner
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import javax.inject.Inject

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class UpdatePropertyUseCaseTest : BaseUseCaseTest<UpdatePropertyUseCase>() {

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
    fun `test update property valid test`() = runTest {
        val listOfProperties = dao.getProperties()
        val updatedProperty = Property
            .from(listOfProperties.first { it.propertyId == TestConstants.ID_VALID_FOR_REMOVING })
            .copy(favorite = false)

        assertThat(listOfProperties.none { !it.favorite }).isEqualTo(true)
        assertThat(listOfProperties.size).isEqualTo(4)
        val params = UpdatePropertyUseCase.Params.create(updatedProperty)
        useCase.execute(params)

        val updatedListOfProperties = dao.getProperties()
        assertThat(updatedListOfProperties.count { !it.favorite }).isEqualTo(1)
        assertThat(updatedListOfProperties.size).isEqualTo(4)
    }
}