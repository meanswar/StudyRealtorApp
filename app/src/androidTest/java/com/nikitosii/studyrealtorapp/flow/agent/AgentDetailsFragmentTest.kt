package com.nikitosii.studyrealtorapp.flow.agent

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.nikitosii.studyrealtorapp.flow.agent.details.AgentDetailsFragment
import com.nikitosii.studyrealtorapp.flow.agent.details.AgentDetailsViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@RunWith(AndroidJUnit4::class)
@MediumTest
@ExperimentalCoroutinesApi
class AgentDetailsFragmentTest {

    @Mock
    private lateinit var viewModel: AgentDetailsViewModel

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun `test`() {

    }
}