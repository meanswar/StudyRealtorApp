package com.nikitosii.studyrealtorapp.domain.usecase.properties

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nikitosii.studyrealtorapp.util.TestConstants
import com.nikitosii.studyrealtorapp.util.TestConstants.ID_INVALID_TEXT
import com.nikitosii.studyrealtorapp.util.TestConstants.ID_VALID_TEXT
import com.nikitosii.studyrealtorapp.core.domain.useCase.properties.GetPropertyDetailsUseCase
import com.nikitosii.studyrealtorapp.core.source.local.model.Address
import com.nikitosii.studyrealtorapp.core.source.local.model.Branding
import com.nikitosii.studyrealtorapp.core.source.local.model.Coordinate
import com.nikitosii.studyrealtorapp.core.source.local.model.County
import com.nikitosii.studyrealtorapp.core.source.local.model.Description
import com.nikitosii.studyrealtorapp.core.source.local.model.HouseType
import com.nikitosii.studyrealtorapp.core.source.local.model.Photo
import com.nikitosii.studyrealtorapp.core.source.local.model.property_details.DetailedLocation
import com.nikitosii.studyrealtorapp.core.source.local.model.property_details.Details
import com.nikitosii.studyrealtorapp.core.source.local.model.property_details.EducationFacilities
import com.nikitosii.studyrealtorapp.core.source.local.model.property_details.PropertyDetails
import com.nikitosii.studyrealtorapp.core.source.local.model.property_details.School
import com.nikitosii.studyrealtorapp.core.source.net.model.source.AgentResponseApi
import com.nikitosii.studyrealtorapp.core.source.net.model.source.SourceResponseApi
import com.nikitosii.studyrealtorapp.core.source.net.model.vrtour.VrTourResponseApi
import com.nikitosii.studyrealtorapp.di.DaggerTestAppComponent
import com.nikitosii.studyrealtorapp.domain.usecase.base.BaseUseCaseTest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class GetPropertyDetailsUseCaseTest : BaseUseCaseTest<GetPropertyDetailsUseCase>() {
    private val testDispatcher = TestCoroutineDispatcher()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    override fun setup() {
        MockitoAnnotations.initMocks(this)
        Dispatchers.setMain(testDispatcher)
        DaggerTestAppComponent.builder()
            .build()
            .inject(this)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `get property details valid test`() = runTest {
        val params = GetPropertyDetailsUseCase.Params.create(ID_VALID_TEXT)
        val result = useCase.execute(params)
        assert(
            result.equals(
                PropertyDetails(
                    branding = listOf(
                        Branding(
                            name = TestConstants.NAME_VALID,
                            photo = TestConstants.IMAGE_VALID,
                            type = TestConstants.ANY_TEXT,
                            accentColor = TestConstants.VALUE_VALID,
                            phone = TestConstants.PHONE_VALID
                        )
                    ),
                    comingSoonDate = TestConstants.SERVER_DATE_PATTERN,
                    description = Description(
                        baths = TestConstants.ANY_DIGITS,
                        bathsGtr1 = TestConstants.ANY_DIGITS,
                        bathsGtr3 = TestConstants.ANY_DIGITS,
                        bathsFull = TestConstants.ANY_DIGITS,
                        bathsHalf = TestConstants.ANY_DIGITS,
                        beds = TestConstants.ANY_DIGITS,
                        garage = TestConstants.ANY_DIGITS,
                        lotSqft = TestConstants.ANY_DIGITS,
                        name = TestConstants.NAME_VALID,
                        sqft = TestConstants.ANY_DIGITS,
                        pool = TestConstants.ANY_TEXT,
                        text = TestConstants.ANY_TEXT,
                        type = HouseType.TOWNHOMES,
                        rooms = TestConstants.ANY_DIGITS
                    ),
                    details = listOf(
                        Details(
                            category = TestConstants.ANY_TEXT,
                            parentCategory = TestConstants.ANY_TEXT,
                            text = listOf(
                                TestConstants.ANY_TEXT,
                                TestConstants.ANY_TEXT,
                                TestConstants.ANY_TEXT
                            )
                        )
                    ),
                    lastUpdateDate = TestConstants.SERVER_DATE_PATTERN,
                    listDate = TestConstants.SERVER_DATE_PATTERN,
                    listPrice = TestConstants.ANY_DIGITS,
                    listingId = TestConstants.ANY_TEXT,
                    location = DetailedLocation(
                        address = Address(
                            city = TestConstants.ANY_TEXT,
                            coordinate = Coordinate(
                                TestConstants.LATITUDE_VALID,
                                TestConstants.LONGITUDE_VALID
                            ),
                            line = TestConstants.ANY_TEXT,
                            postalCode = TestConstants.ANY_TEXT,
                            state = TestConstants.ANY_TEXT,
                            stateCode = TestConstants.ANY_TEXT
                        ),
                        country = TestConstants.ANY_TEXT,
                        line = TestConstants.ANY_TEXT,
                        state = TestConstants.ANY_TEXT,
                        stateCode = TestConstants.ANY_TEXT,
                        county = County(
                            fipsCode = TestConstants.ANY_TEXT,
                            name = TestConstants.ANY_TEXT
                        )
                    ),
                    photos = listOf(
                        Photo(url = TestConstants.PHOTO_VALID),
                        Photo(url = TestConstants.PHOTO_VALID)
                    ),
                    schools = EducationFacilities(
                        schools = listOf(
                            School(
                                id = ID_VALID_TEXT,
                                name = TestConstants.ANY_TEXT,
                                distanceInMiles = TestConstants.ANY_DOUBLE,
                                educationLevels = listOf(TestConstants.ANY_TEXT),
                                fundingType = TestConstants.ANY_TEXT,
                                grades = listOf(
                                    TestConstants.ANY_TEXT,
                                    TestConstants.ANY_TEXT,
                                    TestConstants.ANY_TEXT,
                                    TestConstants.ANY_TEXT,
                                    TestConstants.ANY_TEXT,
                                    TestConstants.ANY_TEXT
                                ),
                                studentCount = TestConstants.ANY_DIGITS,
                                studentTeacherRatio = TestConstants.ANY_DOUBLE,
                                parentRating = TestConstants.ANY_DIGITS,
                                rating = TestConstants.ANY_DIGITS,
                                reviewCount = TestConstants.ANY_DIGITS,
                                slug = TestConstants.ANY_TEXT,
                                slugId = TestConstants.ANY_TEXT
                            )
                        ),
                        total = 1
                    ),
                    source = SourceResponseApi(
                        agents = listOf(
                            AgentResponseApi(
                                officeName = TestConstants.ANY_TEXT
                            )
                        ),
                        id = TestConstants.ANY_TEXT,
                        planId = TestConstants.ANY_TEXT,
                        specId = TestConstants.ANY_TEXT,
                        type = TestConstants.ANY_TEXT
                    ),
                    propertyId = TestConstants.ANY_TEXT,
                    status = TestConstants.ANY_TEXT,
                    tags = listOf(
                        TestConstants.ANY_TEXT,
                        TestConstants.ANY_TEXT,
                        TestConstants.ANY_TEXT
                    ),
                    virtualTours = listOf(
                        VrTourResponseApi(
                            href = TestConstants.URL_VALID,
                            type = TestConstants.ANY_TEXT
                        )
                    )
                )
            )
        )
    }

    @Test
    fun `get property details invalid test`() = runTest {
        val params = GetPropertyDetailsUseCase.Params.create(ID_INVALID_TEXT)
        try {
            useCase.execute(params)
            assert(false)
        } catch (e: Exception) {
            assert(e.message == TestConstants.EXCEPTION_WRONG_PARAMS)
        }
    }
}