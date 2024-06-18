package com.nikitosii.studyrealtorapp.di

import com.nikitosii.studyrealtorapp.domain.usecase.agent.GetAgentDetailsUseCaseTest
import com.nikitosii.studyrealtorapp.domain.usecase.agent.GetAgentsFromNetworkUseCaseTest
import dagger.Component
import dagger.android.AndroidInjectionModule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@Singleton
@Component(
    modules = [
        TestAppModule::class,
        AndroidInjectionModule::class
    ]
)
interface TestAppComponent {

    @Component.Builder
    interface Builder {

        fun build(): TestAppComponent
    }

    fun inject(useCaseTest: GetAgentDetailsUseCaseTest)

    fun inject(useCaseTest: GetAgentsFromNetworkUseCaseTest)
}