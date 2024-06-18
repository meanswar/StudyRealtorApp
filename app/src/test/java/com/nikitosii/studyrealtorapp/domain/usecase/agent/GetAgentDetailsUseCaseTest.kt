package com.nikitosii.studyrealtorapp.domain.usecase.agent

import com.nikitosii.studyrealtorapp.TestConstants
import com.nikitosii.studyrealtorapp.TestConstants.ANY_DIGITS
import com.nikitosii.studyrealtorapp.TestConstants.ANY_FLOAT
import com.nikitosii.studyrealtorapp.TestConstants.ANY_TEXT
import com.nikitosii.studyrealtorapp.TestConstants.EMAIL_VALID
import com.nikitosii.studyrealtorapp.TestConstants.ID_INVALID_TEXT
import com.nikitosii.studyrealtorapp.TestConstants.LATITUDE_VALID
import com.nikitosii.studyrealtorapp.TestConstants.LONGITUDE_VALID
import com.nikitosii.studyrealtorapp.TestConstants.PHONE_EXT_VALID
import com.nikitosii.studyrealtorapp.TestConstants.PHONE_START_WITH_PLUS_VALID
import com.nikitosii.studyrealtorapp.TestConstants.PHONE_VALID
import com.nikitosii.studyrealtorapp.TestConstants.PHOTO_VALID
import com.nikitosii.studyrealtorapp.TestConstants.URL_VALID
import com.nikitosii.studyrealtorapp.core.domain.useCase.agent.GetAgentDetailsUseCase
import com.nikitosii.studyrealtorapp.di.DaggerTestAppComponent
import com.nikitosii.studyrealtorapp.domain.usecase.base.BaseUseCaseTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test


@OptIn(ExperimentalCoroutinesApi::class)
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
        assert(
            result.firstName == ANY_TEXT &&
                    result.lastName == ANY_TEXT &&
                    result.fullName == ANY_TEXT &&
                    result.description == ANY_TEXT &&
                    result.agentRating == ANY_DIGITS &&
                    result.phone == PHONE_VALID &&
                    result.email == EMAIL_VALID &&
                    result.website == ANY_TEXT &&
                    result.photo?.url == PHOTO_VALID &&
                    result.address?.city == ANY_TEXT &&
                    result.address?.coordinate?.latitude == LATITUDE_VALID &&
                    result.address?.coordinate?.longitude == LONGITUDE_VALID &&
                    result.address?.line == ANY_TEXT &&
                    result.address?.postalCode == ANY_TEXT &&
                    result.address?.state == ANY_TEXT &&
                    result.address?.stateCode == ANY_TEXT &&
                    result.broker?.name == ANY_TEXT &&
                    result.broker?.photo?.url == URL_VALID &&
                    result.specialization?.size == 2 &&
                    result.languages.size == 2 &&
                    result.reviewCount == ANY_DIGITS &&
                    result.recommendationsCount == ANY_DIGITS &&
                    result.averageRating == ANY_FLOAT &&
                    result.reviews.size == 2 &&
                    result.reviews[0].author == ANY_TEXT &&
                    result.reviews[0].description == ANY_TEXT &&
                    result.reviews[0].ratingValue == ANY_DIGITS &&
                    result.reviews[1].author == ANY_TEXT &&
                    result.reviews[1].description == ANY_TEXT &&
                    result.reviews[1].ratingValue == ANY_DIGITS &&
                    result.marketingArea.size == 2 &&
                    result.marketingArea[0].name == ANY_TEXT &&
                    result.marketingArea[0].stateCode == ANY_TEXT &&
                    result.marketingArea[1].name == ANY_TEXT &&
                    result.marketingArea[1].stateCode == ANY_TEXT &&
                    result.servedAreas.size == 2 &&
                    result.servedAreas[0].name == ANY_TEXT &&
                    result.servedAreas[0].stateCode == ANY_TEXT &&
                    result.servedAreas[1].name == ANY_TEXT &&
                    result.servedAreas[1].stateCode == ANY_TEXT &&
                    result.phones.size == 2 &&
                    result.phones[0].ext == PHONE_EXT_VALID &&
                    result.phones[0].number == PHONE_VALID &&
                    result.phones[0].primary == true &&
                    result.phones[0].trackable == true &&
                    result.phones[0].type == ANY_TEXT &&
                    result.phones[1].ext == null &&
                    result.phones[1].number == PHONE_START_WITH_PLUS_VALID &&
                    result.phones[1].primary == false &&
                    result.phones[1].trackable == true &&
                    result.phones[1].type == ANY_TEXT
        )
    }

    @Test
    fun `get agent details invalid data test`() = runTest {
        val params = GetAgentDetailsUseCase.Params.create(ID_INVALID_TEXT)
        try {
            useCase.execute(params)
            assert(false)
        } catch (e: Exception) {
            assert(e.message == "wrong agent id")
        }
    }
}