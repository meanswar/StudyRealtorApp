package com.nikitosii.studyrealtorapp.domain.usecase.agent

import com.nikitosii.studyrealtorapp.util.TestConstants
import com.nikitosii.studyrealtorapp.util.TestConstants.ANY_DIGITS
import com.nikitosii.studyrealtorapp.util.TestConstants.ANY_FLOAT
import com.nikitosii.studyrealtorapp.util.TestConstants.ANY_TEXT
import com.nikitosii.studyrealtorapp.util.TestConstants.EMAIL_VALID
import com.nikitosii.studyrealtorapp.util.TestConstants.ID_INVALID_TEXT
import com.nikitosii.studyrealtorapp.util.TestConstants.LATITUDE_VALID
import com.nikitosii.studyrealtorapp.util.TestConstants.LONGITUDE_VALID
import com.nikitosii.studyrealtorapp.util.TestConstants.PHONE_EXT_VALID
import com.nikitosii.studyrealtorapp.util.TestConstants.PHONE_VALID
import com.nikitosii.studyrealtorapp.util.TestConstants.PHOTO_VALID
import com.nikitosii.studyrealtorapp.util.TestConstants.URL_VALID
import com.nikitosii.studyrealtorapp.core.domain.useCase.agent.GetAgentDetailsUseCase
import com.nikitosii.studyrealtorapp.core.source.local.model.Address
import com.nikitosii.studyrealtorapp.core.source.local.model.Coordinate
import com.nikitosii.studyrealtorapp.core.source.local.model.Phone
import com.nikitosii.studyrealtorapp.core.source.local.model.Photo
import com.nikitosii.studyrealtorapp.core.source.local.model.agent.AgentDetails
import com.nikitosii.studyrealtorapp.core.source.local.model.agent.Area
import com.nikitosii.studyrealtorapp.core.source.local.model.agent.Broker
import com.nikitosii.studyrealtorapp.core.source.local.model.agent.Review
import com.nikitosii.studyrealtorapp.di.DaggerTestAppComponent
import com.nikitosii.studyrealtorapp.domain.usecase.base.BaseUseCaseTest
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner


@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class GetAgentDetailsUseCaseTest : BaseUseCaseTest<GetAgentDetailsUseCase>() {

    @Before
    override fun setup() {
        DaggerTestAppComponent.builder()
            .build()
            .inject(this)
    }

    @Test
    fun `get agent details valid test`() = runTest {
        val params = GetAgentDetailsUseCase.Params.create(TestConstants.ID_VALID_TEXT)
        val result = useCase.execute(params)
        assertThat(result).isEqualTo(
            AgentDetails(
                firstName = ANY_TEXT,
                lastName = ANY_TEXT,
                fullName = ANY_TEXT,
                description = ANY_TEXT,
                agentRating = ANY_DIGITS,
                phone = PHONE_VALID,
                email = EMAIL_VALID,
                website = ANY_TEXT,
                photo = Photo(url = PHOTO_VALID),
                address = Address(
                    city = ANY_TEXT,
                    coordinate = Coordinate(LATITUDE_VALID, LONGITUDE_VALID),
                    line = ANY_TEXT,
                    postalCode = ANY_TEXT,
                    state = ANY_TEXT,
                    stateCode = ANY_TEXT
                ),
                broker = Broker(
                    name = ANY_TEXT,
                    photo = Photo(url = URL_VALID)
                ),
                specialization = listOf(ANY_TEXT, ANY_TEXT),

                reviewCount = ANY_DIGITS,
                recommendationsCount = ANY_DIGITS,
                averageRating = ANY_FLOAT,
                reviews = listOf(
                    Review(
                        author = ANY_TEXT,
                        description = ANY_TEXT,
                        ratingValue = ANY_DIGITS
                    ),
                    Review(
                        author = ANY_TEXT,
                        description = ANY_TEXT,
                        ratingValue = ANY_DIGITS
                    )
                ),
                marketingArea = listOf(
                    Area(
                        name = ANY_TEXT,
                        stateCode = ANY_TEXT,
                        imageUrl = TestConstants.IMAGE_TEXT
                    ),
                    Area(
                        name = ANY_TEXT,
                        stateCode = ANY_TEXT,
                        imageUrl = TestConstants.IMAGE_TEXT
                    )
                ),
                servedAreas = listOf(
                    Area(
                        name = ANY_TEXT,
                        stateCode = ANY_TEXT
                    ),
                    Area(
                        name = ANY_TEXT,
                        stateCode = ANY_TEXT
                    )
                ),
                phones = listOf(
                    Phone(
                        ext = PHONE_EXT_VALID,
                        number = PHONE_VALID,
                        primary = true,
                        trackable = true,
                        type = ANY_TEXT
                    ),
                    Phone(
                        ext = null,
                        number = PHONE_VALID,
                        primary = false,
                        trackable = true,
                        type = ANY_TEXT
                    )
                ),
                languages = listOf(ANY_TEXT, ANY_TEXT),
            )
        )
    }

    @Test
    fun `get agent details invalid data test`() = runTest {
        val params = GetAgentDetailsUseCase.Params.create(ID_INVALID_TEXT)
        try {
            useCase.execute(params)
            assert(false)
        } catch (e: Exception) {
            assertThat(e.message).isEqualTo(TestConstants.EXCEPTION_WRONG_PARAMS)
        }
    }
}