package com.nikitosii.studyrealtorapp.domain.usecase.agent

import android.annotation.SuppressLint
import com.nikitosii.studyrealtorapp.TestConstants
import com.nikitosii.studyrealtorapp.TestConstants.ANY_DIGITS
import com.nikitosii.studyrealtorapp.TestConstants.ANY_TEXT
import com.nikitosii.studyrealtorapp.TestConstants.EMAIL_VALID
import com.nikitosii.studyrealtorapp.TestConstants.EMPTY_TEXT
import com.nikitosii.studyrealtorapp.TestConstants.EXCEPTION_WRONG_PARAMS
import com.nikitosii.studyrealtorapp.TestConstants.ID_VALID_TEXT
import com.nikitosii.studyrealtorapp.TestConstants.LATITUDE_VALID
import com.nikitosii.studyrealtorapp.TestConstants.LOCATION_INVALID
import com.nikitosii.studyrealtorapp.TestConstants.LOCATION_VALID
import com.nikitosii.studyrealtorapp.TestConstants.LONGITUDE_VALID
import com.nikitosii.studyrealtorapp.TestConstants.PHONE_EXT_VALID
import com.nikitosii.studyrealtorapp.TestConstants.PHONE_VALID
import com.nikitosii.studyrealtorapp.TestConstants.PHOTO_VALID
import com.nikitosii.studyrealtorapp.TestConstants.REQUEST_DIGITS_INVALID
import com.nikitosii.studyrealtorapp.TestConstants.REQUEST_DIGITS_VALID
import com.nikitosii.studyrealtorapp.TestConstants.REQUEST_TEXT_INVALID
import com.nikitosii.studyrealtorapp.TestConstants.REQUEST_TEXT_VALID
import com.nikitosii.studyrealtorapp.TestConstants.SERVER_DATE_PATTERN
import com.nikitosii.studyrealtorapp.core.source.local.model.agent.Agent
import com.nikitosii.studyrealtorapp.core.source.local.model.agent.AgentRequestApi
import com.nikitosii.studyrealtorapp.core.domain.useCase.agent.GetAgentsFromNetworkUseCase
import com.nikitosii.studyrealtorapp.di.DaggerTestAppComponent
import com.nikitosii.studyrealtorapp.domain.usecase.base.BaseUseCaseTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetAgentsFromNetworkUseCaseTest : BaseUseCaseTest<GetAgentsFromNetworkUseCase>() {

    @Before
    override fun setup() {
        DaggerTestAppComponent.builder()
            .build()
            .inject(this)
    }
    private fun checkResult(result: Agent) {
        assert(
            result.fullName == ANY_TEXT &&
                    result.nickname == ANY_TEXT &&
                    result.name == ANY_TEXT &&
                    result.title == ANY_TEXT &&
                    result.slogan == ANY_TEXT &&
                    result.photoUrl == PHOTO_VALID &&
                    result.backgroundPhotoUrl == PHOTO_VALID &&
                    result.address?.city == ANY_TEXT &&
                    result.address?.coordinate?.latitude == LATITUDE_VALID &&
                    result.address?.coordinate?.longitude == LONGITUDE_VALID &&
                    result.address?.line == ANY_TEXT &&
                    result.address?.postalCode == ANY_TEXT &&
                    result.address?.state == ANY_TEXT &&
                    result.address?.stateCode == ANY_TEXT &&
                    result.office?.email == EMAIL_VALID &&
                    result.office?.id == ID_VALID_TEXT &&
                    result.office?.href == ANY_TEXT &&
                    result.office?.mls_set == ANY_TEXT &&
                    result.office?.name == ANY_TEXT &&
                    result.office?.phones?.size == 1 &&
                    result.office?.phones?.get(0)?.ext == PHONE_EXT_VALID &&
                    result.office?.phones?.get(0)?.number == PHONE_VALID &&
                    result.office?.phones?.get(0)?.primary == true &&
                    result.office?.phones?.get(0)?.trackable == true &&
                    result.office?.phones?.get(0)?.type == ANY_TEXT &&
                    result.office?.image == PHOTO_VALID &&
                    result.phone == PHONE_VALID &&
                    result.webUrl == ANY_TEXT &&
                    result.recentlySoldCount == ANY_DIGITS &&
                    result.forSalePriceCount == ANY_DIGITS &&
                    result.minForSalePrice == ANY_DIGITS &&
                    result.maxForSalePrice == ANY_DIGITS &&
                    result.reviewCount == ANY_DIGITS &&
                    result.recommendationsCount == ANY_DIGITS &&
                    result.id == ID_VALID_TEXT &&
                    result.salePrice?.min == ANY_DIGITS &&
                    result.salePrice?.max == ANY_DIGITS &&
                    result.salePrice?.lastListingDate == SERVER_DATE_PATTERN
        )
    }

    @SuppressLint("CheckResult")
    @Test
    fun `get agents from network test valid`() = runTest {
        val request = AgentRequestApi(
            lang = null,
            price = null,
            photo = null,
            rating = null,
            agentName = null,
            location = LOCATION_VALID
        )
        val params = GetAgentsFromNetworkUseCase.Params.from(request, TestConstants.PAGE_VALID)
        val data = useCase.execute(params)
        assert(data.size == 1)
        checkResult(data.first())
    }

    @Test
    fun `get agents from network empty address`() = runTest {
        val request = AgentRequestApi(
            lang = null,
            price = null,
            photo = null,
            rating = null,
            agentName = null,
            location = EMPTY_TEXT
        )
        val params = GetAgentsFromNetworkUseCase.Params.from(request, TestConstants.PAGE_VALID)
        try {
            useCase.execute(params)
            assert(false)
        } catch (e: Exception) {
            assert(e.message == EXCEPTION_WRONG_PARAMS)
        }
    }

    @Test
    fun `get agents from network invalid address`() = runTest {
        val request = AgentRequestApi(
            lang = null,
            price = null,
            photo = null,
            rating = null,
            agentName = null,
            location = LOCATION_INVALID
        )
        val params = GetAgentsFromNetworkUseCase.Params.from(request, TestConstants.PAGE_VALID)
        try {
            useCase.execute(params)
            assert(false)
        } catch (e: Exception) {
            assert(e.message == EXCEPTION_WRONG_PARAMS)
        }
    }

    @Test
    fun `get agents from network invalid address and agentName`() = runTest {
        val request = AgentRequestApi(
            lang = null,
            price = null,
            photo = null,
            rating = null,
            agentName = REQUEST_TEXT_INVALID,
            location = LOCATION_INVALID
        )
        val params = GetAgentsFromNetworkUseCase.Params.from(request, TestConstants.PAGE_VALID)
        try {
            useCase.execute(params)
            assert(false)
        } catch (e: Exception) {
            assert(e.message == EXCEPTION_WRONG_PARAMS)
        }
    }

    @Test
    fun `get agents from network invalid address, agentName and rating`() = runTest {
        val request = AgentRequestApi(
            lang = null,
            price = null,
            photo = null,
            rating = REQUEST_DIGITS_INVALID,
            agentName = REQUEST_TEXT_INVALID,
            location = LOCATION_INVALID
        )
        val params = GetAgentsFromNetworkUseCase.Params.from(request, TestConstants.PAGE_VALID)
        try {
            useCase.execute(params)
            assert(false)
        } catch (e: Exception) {
            assert(e.message == EXCEPTION_WRONG_PARAMS)
        }
    }

    @Test
    fun `get agents from network invalid address, agentName, rating and price`() = runTest {
        val request = AgentRequestApi(
            lang = null,
            price = REQUEST_TEXT_INVALID,
            photo = null,
            rating = REQUEST_DIGITS_INVALID,
            agentName = REQUEST_TEXT_INVALID,
            location = LOCATION_INVALID
        )
        val params = GetAgentsFromNetworkUseCase.Params.from(request, TestConstants.PAGE_VALID)
        try {
            useCase.execute(params)
            assert(false)
        } catch (e: Exception) {
            assert(e.message == EXCEPTION_WRONG_PARAMS)
        }
    }

    @Test
    fun `get agents from network invalid params`() = runTest {
        val request = AgentRequestApi(
            lang = REQUEST_TEXT_INVALID,
            price = REQUEST_TEXT_INVALID,
            photo = null,
            rating = REQUEST_DIGITS_INVALID,
            agentName = REQUEST_TEXT_INVALID,
            location = LOCATION_INVALID
        )
        val params = GetAgentsFromNetworkUseCase.Params.from(request, TestConstants.PAGE_VALID)
        try {
            useCase.execute(params)
            assert(false)
        } catch (e: Exception) {
            assert(e.message == EXCEPTION_WRONG_PARAMS)
        }
    }

    @Test
    fun `get agents from network valid address, invalid agentName`() = runTest {
        val request = AgentRequestApi(
            lang = null,
            price = null,
            photo = null,
            rating = null,
            agentName = REQUEST_TEXT_INVALID,
            location = LOCATION_VALID
        )
        val params = GetAgentsFromNetworkUseCase.Params.from(request, TestConstants.PAGE_VALID)
        try {
            useCase.execute(params)
            assert(false)
        } catch (e: Exception) {
            assert(e.message == EXCEPTION_WRONG_PARAMS)
        }
    }

    @Test
    fun `get agents from network valid address, invalid agentName and rating`() = runTest {
        val request = AgentRequestApi(
            lang = null,
            price = null,
            photo = null,
            rating = REQUEST_DIGITS_INVALID,
            agentName = REQUEST_TEXT_INVALID,
            location = LOCATION_VALID
        )
        val params = GetAgentsFromNetworkUseCase.Params.from(request, TestConstants.PAGE_VALID)
        try {
            useCase.execute(params)
            assert(false)
        } catch (e: Exception) {
            assert(e.message == EXCEPTION_WRONG_PARAMS)
        }
    }

    @Test
    fun `get agents from network valid address, invalid agentName, rating and price`() = runTest {
        val request = AgentRequestApi(
            lang = null,
            price = REQUEST_TEXT_INVALID,
            photo = null,
            rating = REQUEST_DIGITS_INVALID,
            agentName = REQUEST_TEXT_INVALID,
            location = LOCATION_VALID
        )
        val params = GetAgentsFromNetworkUseCase.Params.from(request, TestConstants.PAGE_VALID)
        try {
            useCase.execute(params)
            assert(false)
        } catch (e: Exception) {
            assert(e.message == EXCEPTION_WRONG_PARAMS)
        }
    }

    @Test
    fun `get agents from network valid address, invalid others`() = runTest {
        val request = AgentRequestApi(
            lang = REQUEST_TEXT_INVALID,
            price = REQUEST_TEXT_INVALID,
            photo = null,
            rating = REQUEST_DIGITS_INVALID,
            agentName = REQUEST_TEXT_INVALID,
            location = LOCATION_VALID
        )
        val params = GetAgentsFromNetworkUseCase.Params.from(request, TestConstants.PAGE_VALID)
        try {
            useCase.execute(params)
            assert(false)
        } catch (e: Exception) {
            assert(e.message == EXCEPTION_WRONG_PARAMS)
        }
    }

    @Test
    fun `get agents from network valid address, invalid rating`() = runTest {
        val request = AgentRequestApi(
            lang = null,
            price = null,
            photo = null,
            rating = REQUEST_DIGITS_INVALID,
            agentName = null,
            location = LOCATION_VALID
        )
        val params = GetAgentsFromNetworkUseCase.Params.from(request, TestConstants.PAGE_VALID)
        try {
            useCase.execute(params)
            assert(false)
        } catch (e: Exception) {
            assert(e.message == EXCEPTION_WRONG_PARAMS)
        }
    }

    @Test
    fun `get agents from network valid address, invalid rating and price`() = runTest {
        val request = AgentRequestApi(
            lang = null,
            price = REQUEST_TEXT_INVALID,
            photo = null,
            rating = REQUEST_DIGITS_INVALID,
            agentName = null,
            location = LOCATION_VALID
        )
        val params = GetAgentsFromNetworkUseCase.Params.from(request, TestConstants.PAGE_VALID)
        try {
            useCase.execute(params)
            assert(false)
        } catch (e: Exception) {
            assert(e.message == EXCEPTION_WRONG_PARAMS)
        }
    }

    @Test
    fun `get agents from network valid address, invalid rating, price, lang`() = runTest {
        val request = AgentRequestApi(
            lang = REQUEST_TEXT_INVALID,
            price = REQUEST_TEXT_INVALID,
            photo = null,
            rating = REQUEST_DIGITS_INVALID,
            agentName = null,
            location = LOCATION_VALID
        )
        val params = GetAgentsFromNetworkUseCase.Params.from(request, TestConstants.PAGE_VALID)
        try {
            useCase.execute(params)
            assert(false)
        } catch (e: Exception) {
            assert(e.message == EXCEPTION_WRONG_PARAMS)
        }
    }

    @Test
    fun `get agents from network valid address, agentName, invalid rating`() = runTest {
        val request = AgentRequestApi(
            lang = null,
            price = null,
            photo = null,
            rating = REQUEST_DIGITS_INVALID,
            agentName = REQUEST_TEXT_VALID,
            location = LOCATION_VALID
        )
        val params = GetAgentsFromNetworkUseCase.Params.from(request, TestConstants.PAGE_VALID)
        try {
            useCase.execute(params)
            assert(false)
        } catch (e: Exception) {
            assert(e.message == EXCEPTION_WRONG_PARAMS)
        }
    }

    @Test
    fun `get agents from network valid address, agentName, invalid rating and price`() = runTest {
        val request = AgentRequestApi(
            lang = null,
            price = REQUEST_TEXT_INVALID,
            photo = null,
            rating = REQUEST_DIGITS_INVALID,
            agentName = REQUEST_TEXT_VALID,
            location = LOCATION_VALID
        )
        val params = GetAgentsFromNetworkUseCase.Params.from(request, TestConstants.PAGE_VALID)
        try {
            useCase.execute(params)
            assert(false)
        } catch (e: Exception) {
            assert(e.message == EXCEPTION_WRONG_PARAMS)
        }
    }

    @Test
    fun `get agents from network valid address, agentName, invalid rating, lang and price`() =
        runTest {
            val request = AgentRequestApi(
                lang = REQUEST_TEXT_INVALID,
                price = REQUEST_TEXT_INVALID,
                photo = null,
                rating = REQUEST_DIGITS_INVALID,
                agentName = REQUEST_TEXT_VALID,
                location = LOCATION_VALID
            )
            val params = GetAgentsFromNetworkUseCase.Params.from(request, TestConstants.PAGE_VALID)
            try {
                useCase.execute(params)
                assert(false)
            } catch (e: Exception) {
                assert(e.message == EXCEPTION_WRONG_PARAMS)
            }
        }

    @Test
    fun `get agents from network valid address, agentName, invalid lang and price`() = runTest {
        val request = AgentRequestApi(
            lang = REQUEST_TEXT_INVALID,
            price = REQUEST_TEXT_INVALID,
            photo = null,
            rating = null,
            agentName = REQUEST_TEXT_VALID,
            location = LOCATION_VALID
        )
        val params = GetAgentsFromNetworkUseCase.Params.from(request, TestConstants.PAGE_VALID)
        try {
            useCase.execute(params)
            assert(false)
        } catch (e: Exception) {
            assert(e.message == EXCEPTION_WRONG_PARAMS)
        }
    }

    @Test
    fun `get agents from network valid address, agentName, invalid lang`() = runTest {
        val request = AgentRequestApi(
            lang = REQUEST_TEXT_INVALID,
            price = null,
            photo = null,
            rating = null,
            agentName = REQUEST_TEXT_VALID,
            location = LOCATION_VALID
        )
        val params = GetAgentsFromNetworkUseCase.Params.from(request, TestConstants.PAGE_VALID)
        try {
            useCase.execute(params)
            assert(false)
        } catch (e: Exception) {
            assert(e.message == EXCEPTION_WRONG_PARAMS)
        }
    }

    @Test
    fun `get agents from network valid address, agentName, invalid price`() = runTest {
        val request = AgentRequestApi(
            lang = null,
            price = REQUEST_TEXT_INVALID,
            photo = null,
            rating = null,
            agentName = REQUEST_TEXT_VALID,
            location = LOCATION_VALID
        )
        val params = GetAgentsFromNetworkUseCase.Params.from(request, TestConstants.PAGE_VALID)
        try {
            useCase.execute(params)
            assert(false)
        } catch (e: Exception) {
            assert(e.message == EXCEPTION_WRONG_PARAMS)
        }
    }

    @Test
    fun `get agents from network valid address, agentName rating, invalid price`() = runTest {
        val request = AgentRequestApi(
            lang = null,
            price = REQUEST_TEXT_INVALID,
            photo = null,
            rating = REQUEST_DIGITS_VALID,
            agentName = REQUEST_TEXT_VALID,
            location = LOCATION_VALID
        )
        val params = GetAgentsFromNetworkUseCase.Params.from(request, TestConstants.PAGE_VALID)
        try {
            useCase.execute(params)
            assert(false)
        } catch (e: Exception) {
            assert(e.message == EXCEPTION_WRONG_PARAMS)
        }
    }

    @Test
    fun `get agents from network valid address, agentName rating, invalid price and lang`() =
        runTest {
            val request = AgentRequestApi(
                lang = REQUEST_TEXT_INVALID,
                price = REQUEST_TEXT_INVALID,
                photo = null,
                rating = REQUEST_DIGITS_VALID,
                agentName = REQUEST_TEXT_VALID,
                location = LOCATION_VALID
            )
            val params = GetAgentsFromNetworkUseCase.Params.from(request, TestConstants.PAGE_VALID)
            try {
                useCase.execute(params)
                assert(false)
            } catch (e: Exception) {
                assert(e.message == EXCEPTION_WRONG_PARAMS)
            }
        }

    @Test
    fun `get agents from network valid address, agentName rating, invalid lang`() = runTest {
        val request = AgentRequestApi(
            lang = REQUEST_TEXT_INVALID,
            price = null,
            photo = null,
            rating = REQUEST_DIGITS_VALID,
            agentName = REQUEST_TEXT_VALID,
            location = LOCATION_VALID
        )
        val params = GetAgentsFromNetworkUseCase.Params.from(request, TestConstants.PAGE_VALID)
        try {
            useCase.execute(params)
            assert(false)
        } catch (e: Exception) {
            assert(e.message == EXCEPTION_WRONG_PARAMS)
        }
    }

    @Test
    fun `get agents from network valid address, agentName rating, price, invalid lang`() = runTest {
        val request = AgentRequestApi(
            lang = REQUEST_TEXT_INVALID,
            price = REQUEST_TEXT_VALID,
            photo = null,
            rating = REQUEST_DIGITS_VALID,
            agentName = REQUEST_TEXT_VALID,
            location = LOCATION_VALID
        )
        val params = GetAgentsFromNetworkUseCase.Params.from(request, TestConstants.PAGE_VALID)
        try {
            useCase.execute(params)
            assert(false)
        } catch (e: Exception) {
            assert(e.message == EXCEPTION_WRONG_PARAMS)
        }
    }

    @Test

    fun `get agents from network valid all, invalid page`() = runTest {
        val request = AgentRequestApi(
            lang = REQUEST_TEXT_VALID,
            price = REQUEST_TEXT_VALID,
            photo = null,
            rating = REQUEST_DIGITS_VALID,
            agentName = REQUEST_TEXT_VALID,
            location = LOCATION_VALID
        )
        val params = GetAgentsFromNetworkUseCase.Params.from(request, TestConstants.PAGE_INVALID)
        try {
            useCase.execute(params)
            assert(false)
        } catch (e: Exception) {
            assert(e.message == EXCEPTION_WRONG_PARAMS)
        }
    }

    @Test
    fun `get agents from network valid all`() = runTest {
        val request = AgentRequestApi(
            lang = REQUEST_TEXT_VALID,
            price = REQUEST_TEXT_VALID,
            photo = null,
            rating = REQUEST_DIGITS_VALID,
            agentName = REQUEST_TEXT_VALID,
            location = LOCATION_VALID
        )
        val params = GetAgentsFromNetworkUseCase.Params.from(request, TestConstants.PAGE_VALID)
        val data = useCase.execute(params)
        assert(data.size == 1)
        checkResult(data.first())
    }

    @Test
    fun `get agents from network valid location and agentName`() = runTest {
        val request = AgentRequestApi(
            lang = null,
            price = null,
            photo = null,
            rating = null,
            agentName = REQUEST_TEXT_VALID,
            location = LOCATION_VALID
        )
        val params = GetAgentsFromNetworkUseCase.Params.from(request, TestConstants.PAGE_VALID)
        val data = useCase.execute(params)
        assert(data.size == 1)
        checkResult(data.first())
    }

    @Test
    fun `get agents from network valid location, rating and agentName`() = runTest {
        val request = AgentRequestApi(
            lang = null,
            price = null,
            photo = null,
            rating = REQUEST_DIGITS_VALID,
            agentName = REQUEST_TEXT_VALID,
            location = LOCATION_VALID
        )
        val params = GetAgentsFromNetworkUseCase.Params.from(request, TestConstants.PAGE_VALID)
        val data = useCase.execute(params)
        assert(data.size == 1)
        checkResult(data.first())
    }

    @Test
    fun `get agents from network valid location, rating, price and agentName`() = runTest {
        val request = AgentRequestApi(
            lang = null,
            price = REQUEST_TEXT_VALID,
            photo = null,
            rating = REQUEST_DIGITS_VALID,
            agentName = REQUEST_TEXT_VALID,
            location = LOCATION_VALID
        )
        val params = GetAgentsFromNetworkUseCase.Params.from(request, TestConstants.PAGE_VALID)
        val data = useCase.execute(params)
        assert(data.size == 1)
        checkResult(data.first())
    }

    @Test
    fun `get agents from network valid location and rating`() = runTest {
        val request = AgentRequestApi(
            lang = null,
            price = null,
            photo = null,
            rating = REQUEST_DIGITS_VALID,
            agentName = null,
            location = LOCATION_VALID
        )
        val params = GetAgentsFromNetworkUseCase.Params.from(request, TestConstants.PAGE_VALID)
        val data = useCase.execute(params)
        assert(data.size == 1)
        checkResult(data.first())
    }

    @Test
    fun `get agents from network valid location, price and rating`() = runTest {
        val request = AgentRequestApi(
            lang = null,
            price = REQUEST_TEXT_VALID,
            photo = null,
            rating = REQUEST_DIGITS_VALID,
            agentName = null,
            location = LOCATION_VALID
        )
        val params = GetAgentsFromNetworkUseCase.Params.from(request, TestConstants.PAGE_VALID)
        val data = useCase.execute(params)
        assert(data.size == 1)
        checkResult(data.first())
    }

    @Test
    fun `get agents from network valid location and price`() = runTest {
        val request = AgentRequestApi(
            lang = null,
            price = REQUEST_TEXT_VALID,
            photo = null,
            rating = null,
            agentName = null,
            location = LOCATION_VALID
        )
        val params = GetAgentsFromNetworkUseCase.Params.from(request, TestConstants.PAGE_VALID)
        val data = useCase.execute(params)
        assert(data.size == 1)
        checkResult(data.first())
    }

    @Test
    fun `get agents from network valid location, price, lang and rating`() = runTest {
        val request = AgentRequestApi(
            lang = REQUEST_TEXT_VALID,
            price = REQUEST_TEXT_VALID,
            photo = null,
            rating = REQUEST_DIGITS_VALID,
            agentName = null,
            location = LOCATION_VALID
        )
        val params = GetAgentsFromNetworkUseCase.Params.from(request, TestConstants.PAGE_VALID)
        val data = useCase.execute(params)
        assert(data.size == 1)
        checkResult(data.first())
    }

    @Test
    fun `get agents from network valid location, lang and price`() = runTest {
        val request = AgentRequestApi(
            lang = REQUEST_TEXT_VALID,
            price = REQUEST_TEXT_VALID,
            photo = null,
            rating = null,
            agentName = null,
            location = LOCATION_VALID
        )
        val params = GetAgentsFromNetworkUseCase.Params.from(request, TestConstants.PAGE_VALID)
        val data = useCase.execute(params)
        assert(data.size == 1)
        val result = data.first()
        checkResult(data.first())
    }

    @Test
    fun `get agents from network valid location and lang`() = runTest {
        val request = AgentRequestApi(
            lang = REQUEST_TEXT_VALID,
            price = null,
            photo = null,
            rating = null,
            agentName = null,
            location = LOCATION_VALID
        )
        val params = GetAgentsFromNetworkUseCase.Params.from(request, TestConstants.PAGE_VALID)
        val data = useCase.execute(params)
        assert(data.size == 1)
        val result = data.first()
        checkResult(data.first())
    }
}
