package com.nikitosii.studyrealtorapp.domain.usecase.agent

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nikitosii.studyrealtorapp.core.domain.useCase.agent.GetLocalAgentsUseCase
import com.nikitosii.studyrealtorapp.core.source.db.dao.AgentDao
import com.nikitosii.studyrealtorapp.core.source.local.model.Address
import com.nikitosii.studyrealtorapp.core.source.local.model.Coordinate
import com.nikitosii.studyrealtorapp.core.source.local.model.Office
import com.nikitosii.studyrealtorapp.core.source.local.model.Phone
import com.nikitosii.studyrealtorapp.core.source.local.model.agent.Agent
import com.nikitosii.studyrealtorapp.core.source.local.model.agent.SalePrice
import com.nikitosii.studyrealtorapp.di.DaggerTestAppComponent
import com.nikitosii.studyrealtorapp.domain.usecase.base.BaseUseCaseTest
import com.nikitosii.studyrealtorapp.util.TestConstants
import com.nikitosii.studyrealtorapp.util.Utils.getDefaultData
import dev.olog.flow.test.observer.test
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
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
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class GetLocalAgentsUseCaseTest : BaseUseCaseTest<GetLocalAgentsUseCase>() {

    private val testDispatcher = TestCoroutineDispatcher()

    @MockK
    private lateinit var dao: AgentDao

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    override fun setup() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)
        DaggerTestAppComponent.builder()
            .build()
            .inject(this)

        dao.getDefaultData()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `get local agents test`() = runTest {
        useCase.execute().test(this) {
            assertValue { data ->
                data.obj?.containsAll(
                    listOf(
                        Agent(
                            id = TestConstants.ID_VALID_TEXT,
                            fullName = TestConstants.NAME_VALID,
                            nickname = TestConstants.EMPTY_TEXT,
                            name = TestConstants.NAME_VALID,
                            title = TestConstants.ANY_TEXT,
                            slogan = TestConstants.ANY_TEXT,
                            photoUrl = TestConstants.PHOTO_VALID,
                            backgroundPhotoUrl = TestConstants.PHOTO_VALID,
                            address = Address(
                                city = TestConstants.LOCATION_VALID,
                                coordinate = Coordinate(
                                    latitude = TestConstants.LATITUDE_VALID,
                                    longitude = TestConstants.LONGITUDE_VALID
                                ),
                                line = TestConstants.ADDRESS_VALID,
                                postalCode = TestConstants.ANY_TEXT,
                                state = TestConstants.ANY_TEXT,
                                stateCode = TestConstants.ANY_TEXT
                            ),
                            office = Office(
                                email = TestConstants.EMAIL_VALID,
                                id = TestConstants.ID_VALID_TEXT,
                                href = TestConstants.URL_VALID,
                                mls_set = TestConstants.STATUS_FOR_SALE,
                                name = TestConstants.NAME_VALID,
                                phones = listOf(
                                    Phone(
                                        ext = TestConstants.PHONE_EXT_VALID,
                                        number = TestConstants.PHONE_VALID,
                                        primary = TestConstants.BOOLEAN_VALID,
                                        trackable = TestConstants.BOOLEAN_VALID,
                                        type = TestConstants.ANY_TEXT
                                    )
                                ),
                                image = TestConstants.IMAGE_VALID
                            ),
                            phone = TestConstants.PHONE_VALID,
                            webUrl = TestConstants.URL_VALID,
                            recentlySoldCount = TestConstants.ANY_DIGITS,
                            forSalePriceCount = TestConstants.ANY_DIGITS,
                            minForSalePrice = TestConstants.PRICE_VALID,
                            maxForSalePrice = TestConstants.ANY_DIGITS,
                            reviewCount = TestConstants.ANY_DIGITS,
                            recommendationsCount = TestConstants.ANY_DIGITS,
                            favorite = TestConstants.BOOLEAN_VALID,
                            salePrice = SalePrice(
                                min = TestConstants.PRICE_VALID,
                                max = TestConstants.ANY_DIGITS,
                                lastListingDate = TestConstants.SERVER_DATE_PATTERN
                            )
                        ),
                        Agent(
                            id = TestConstants.ID_VALID_TEXT,
                            fullName = TestConstants.NAME_VALID,
                            nickname = TestConstants.EMPTY_TEXT,
                            name = TestConstants.NAME_VALID,
                            title = TestConstants.ANY_TEXT,
                            slogan = TestConstants.ANY_TEXT,
                            photoUrl = TestConstants.PHOTO_VALID,
                            backgroundPhotoUrl = TestConstants.PHOTO_VALID,
                            address = Address(
                                city = TestConstants.LOCATION_VALID,
                                coordinate = Coordinate(
                                    latitude = TestConstants.LATITUDE_VALID,
                                    longitude = TestConstants.LONGITUDE_VALID
                                ),
                                line = TestConstants.ADDRESS_VALID,
                                postalCode = TestConstants.ANY_TEXT,
                                state = TestConstants.ANY_TEXT,
                                stateCode = TestConstants.ANY_TEXT
                            ),
                            office = Office(
                                email = TestConstants.EMAIL_VALID,
                                id = TestConstants.ID_VALID_TEXT,
                                href = TestConstants.URL_VALID,
                                mls_set = TestConstants.STATUS_FOR_SALE,
                                name = TestConstants.NAME_VALID,
                                phones = listOf(
                                    Phone(
                                        ext = TestConstants.PHONE_EXT_VALID,
                                        number = TestConstants.PHONE_VALID,
                                        primary = TestConstants.BOOLEAN_VALID,
                                        trackable = TestConstants.BOOLEAN_VALID,
                                        type = TestConstants.ANY_TEXT
                                    )
                                ),
                                image = TestConstants.IMAGE_VALID
                            ),
                            phone = TestConstants.PHONE_VALID,
                            webUrl = TestConstants.URL_VALID,
                            recentlySoldCount = TestConstants.ANY_DIGITS,
                            forSalePriceCount = TestConstants.ANY_DIGITS,
                            minForSalePrice = TestConstants.PRICE_VALID,
                            maxForSalePrice = TestConstants.ANY_DIGITS,
                            reviewCount = TestConstants.ANY_DIGITS,
                            recommendationsCount = TestConstants.ANY_DIGITS,
                            favorite = TestConstants.BOOLEAN_INVALID,
                            salePrice = SalePrice(
                                min = TestConstants.PRICE_VALID,
                                max = TestConstants.ANY_DIGITS,
                                lastListingDate = TestConstants.SERVER_DATE_PATTERN
                            )
                        ),
                        Agent(
                            id = TestConstants.ID_VALID_TEXT,
                            fullName = TestConstants.NAME_VALID,
                            nickname = TestConstants.EMPTY_TEXT,
                            name = TestConstants.NAME_VALID,
                            title = TestConstants.ANY_TEXT,
                            slogan = TestConstants.ANY_TEXT,
                            photoUrl = TestConstants.PHOTO_VALID,
                            backgroundPhotoUrl = TestConstants.PHOTO_VALID,
                            address = Address(
                                city = TestConstants.LOCATION_VALID,
                                coordinate = Coordinate(
                                    latitude = TestConstants.LATITUDE_VALID,
                                    longitude = TestConstants.LONGITUDE_VALID
                                ),
                                line = TestConstants.ADDRESS_VALID,
                                postalCode = TestConstants.ANY_TEXT,
                                state = TestConstants.ANY_TEXT,
                                stateCode = TestConstants.ANY_TEXT
                            ),
                            office = Office(
                                email = TestConstants.EMAIL_VALID,
                                id = TestConstants.ID_VALID_TEXT,
                                href = TestConstants.URL_VALID,
                                mls_set = TestConstants.STATUS_FOR_SALE,
                                name = TestConstants.NAME_VALID,
                                phones = listOf(
                                    Phone(
                                        ext = TestConstants.PHONE_EXT_VALID,
                                        number = TestConstants.PHONE_VALID,
                                        primary = TestConstants.BOOLEAN_VALID,
                                        trackable = TestConstants.BOOLEAN_VALID,
                                        type = TestConstants.ANY_TEXT
                                    )
                                ),
                                image = TestConstants.IMAGE_VALID
                            ),
                            phone = TestConstants.PHONE_VALID,
                            webUrl = TestConstants.URL_VALID,
                            recentlySoldCount = TestConstants.ANY_DIGITS,
                            forSalePriceCount = TestConstants.ANY_DIGITS,
                            minForSalePrice = TestConstants.PRICE_VALID,
                            maxForSalePrice = TestConstants.ANY_DIGITS,
                            reviewCount = TestConstants.ANY_DIGITS,
                            recommendationsCount = TestConstants.ANY_DIGITS,
                            favorite = TestConstants.BOOLEAN_VALID,
                            salePrice = SalePrice(
                                min = TestConstants.PRICE_VALID,
                                max = TestConstants.ANY_DIGITS,
                                lastListingDate = TestConstants.SERVER_DATE_PATTERN
                            )
                        ),
                        Agent(
                            id = TestConstants.ID_VALID_TEXT,
                            fullName = TestConstants.NAME_VALID,
                            nickname = TestConstants.EMPTY_TEXT,
                            name = TestConstants.NAME_VALID,
                            title = TestConstants.ANY_TEXT,
                            slogan = TestConstants.ANY_TEXT,
                            photoUrl = TestConstants.PHOTO_VALID,
                            backgroundPhotoUrl = TestConstants.PHOTO_VALID,
                            address = Address(
                                city = TestConstants.LOCATION_VALID,
                                coordinate = Coordinate(
                                    latitude = TestConstants.LATITUDE_VALID,
                                    longitude = TestConstants.LONGITUDE_VALID
                                ),
                                line = TestConstants.ADDRESS_VALID,
                                postalCode = TestConstants.ANY_TEXT,
                                state = TestConstants.ANY_TEXT,
                                stateCode = TestConstants.ANY_TEXT
                            ),
                            office = Office(
                                email = TestConstants.EMAIL_VALID,
                                id = TestConstants.ID_VALID_TEXT,
                                href = TestConstants.URL_VALID,
                                mls_set = TestConstants.STATUS_FOR_SALE,
                                name = TestConstants.NAME_VALID,
                                phones = listOf(
                                    Phone(
                                        ext = TestConstants.PHONE_EXT_VALID,
                                        number = TestConstants.PHONE_VALID,
                                        primary = TestConstants.BOOLEAN_VALID,
                                        trackable = TestConstants.BOOLEAN_VALID,
                                        type = TestConstants.ANY_TEXT
                                    )
                                ),
                                image = TestConstants.IMAGE_VALID
                            ),
                            phone = TestConstants.PHONE_VALID,
                            webUrl = TestConstants.URL_VALID,
                            recentlySoldCount = TestConstants.ANY_DIGITS,
                            forSalePriceCount = TestConstants.ANY_DIGITS,
                            minForSalePrice = TestConstants.PRICE_VALID,
                            maxForSalePrice = TestConstants.ANY_DIGITS,
                            reviewCount = TestConstants.ANY_DIGITS,
                            recommendationsCount = TestConstants.ANY_DIGITS,
                            favorite = TestConstants.BOOLEAN_INVALID,
                            salePrice = SalePrice(
                                min = TestConstants.PRICE_VALID,
                                max = TestConstants.ANY_DIGITS,
                                lastListingDate = TestConstants.SERVER_DATE_PATTERN
                            )
                        )
                    )
                ) == true
            }
        }
    }
}