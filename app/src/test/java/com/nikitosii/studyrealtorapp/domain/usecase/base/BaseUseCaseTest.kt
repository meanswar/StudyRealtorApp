package com.nikitosii.studyrealtorapp.domain.usecase.base

import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import javax.inject.Inject

@OptIn(DelicateCoroutinesApi::class)
@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
abstract class BaseUseCaseTest<T : Any> {
    @Inject
    lateinit var useCase: T

    @Before
    abstract fun setup()
}