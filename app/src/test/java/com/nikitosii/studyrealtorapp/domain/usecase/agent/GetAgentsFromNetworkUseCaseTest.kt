package com.nikitosii.studyrealtorapp.domain.usecase.agent

import android.annotation.SuppressLint
import com.nikitosii.studyrealtorapp.core.domain.useCase.agent.GetAgentsFromNetworkUseCase
import com.nikitosii.studyrealtorapp.core.source.local.model.agent.Agent
import com.nikitosii.studyrealtorapp.core.source.local.model.agent.AgentRequestApi
import com.nikitosii.studyrealtorapp.di.DaggerTestAppComponent
import com.nikitosii.studyrealtorapp.domain.usecase.base.BaseUseCaseTest
import com.nikitosii.studyrealtorapp.util.AgentTestUtils
import com.nikitosii.studyrealtorapp.util.TestConstants
import com.nikitosii.studyrealtorapp.util.TestConstants.EMPTY_TEXT
import com.nikitosii.studyrealtorapp.util.TestConstants.EXCEPTION_WRONG_PARAMS
import com.nikitosii.studyrealtorapp.util.TestConstants.LOCATION_INVALID
import com.nikitosii.studyrealtorapp.util.TestConstants.LOCATION_VALID
import com.nikitosii.studyrealtorapp.util.TestConstants.REQUEST_DIGITS_INVALID
import com.nikitosii.studyrealtorapp.util.TestConstants.REQUEST_DIGITS_VALID
import com.nikitosii.studyrealtorapp.util.TestConstants.REQUEST_TEXT_INVALID
import com.nikitosii.studyrealtorapp.util.TestConstants.REQUEST_TEXT_VALID
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class GetAgentsFromNetworkUseCaseTest : BaseUseCaseTest<GetAgentsFromNetworkUseCase>() {

    @Before
    override fun setup() {
        DaggerTestAppComponent.builder()
            .build()
            .inject(this)
    }

    private fun checkResult(result: List<Agent>) {
        val expected = AgentTestUtils.getAgentsFromNetwork().agents?.map { Agent.from(it) }
        assertEquals(expected, result)
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
        checkResult(useCase.execute(params))
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
        checkResult(data)
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
        checkResult(data)
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
        checkResult(useCase.execute(params))
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
        checkResult(useCase.execute(params))
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
        checkResult(useCase.execute(params))
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
        checkResult(useCase.execute(params))
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
        checkResult(useCase.execute(params))
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
        checkResult(useCase.execute(params))
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
        checkResult(useCase.execute(params))
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
        checkResult(useCase.execute(params))
    }
}
