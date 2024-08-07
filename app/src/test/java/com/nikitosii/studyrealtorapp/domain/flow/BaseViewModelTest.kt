package com.nikitosii.studyrealtorapp.domain.flow

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nikitosii.studyrealtorapp.flow.base.BaseViewModel
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.Rule
import org.junit.rules.TestRule

abstract class BaseViewModelTest<T : BaseViewModel> {

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    val testDispatcher = TestCoroutineDispatcher()

    lateinit var viewModel: T
}