package com.nikitosii.studyrealtorapp.di

import com.nikitosii.studyrealtorapp.domain.usecase.agent.GetAgentDetailsUseCaseTest
import com.nikitosii.studyrealtorapp.domain.usecase.agent.GetAgentsFromNetworkUseCaseTest
import com.nikitosii.studyrealtorapp.domain.usecase.agent.GetLocalAgentsUseCaseTest
import com.nikitosii.studyrealtorapp.domain.usecase.agent.GetRecentFavoriteAgentsUseCaseTest
import com.nikitosii.studyrealtorapp.domain.usecase.agent.RemoveAgentUseCaseTest
import com.nikitosii.studyrealtorapp.domain.usecase.agent.UpdateAgentFavoriteStatusUseCaseTest
import com.nikitosii.studyrealtorapp.domain.usecase.base.BaseUseCaseTest
import com.nikitosii.studyrealtorapp.domain.usecase.profile.GetProfileFlowUseCaseTest
import com.nikitosii.studyrealtorapp.domain.usecase.profile.RemoveDataUseCaseTest
import com.nikitosii.studyrealtorapp.domain.usecase.profile.RemoveProfileDataUseCaseTest
import com.nikitosii.studyrealtorapp.domain.usecase.profile.UpdateProfileDataUseCaseTest
import com.nikitosii.studyrealtorapp.domain.usecase.properties.GetAllLocalPropertiesUseCaseTest
import com.nikitosii.studyrealtorapp.domain.usecase.properties.GetLocalPropertiesUseCaseTest
import com.nikitosii.studyrealtorapp.domain.usecase.properties.GetLocalPropertyUseCaseTest
import com.nikitosii.studyrealtorapp.domain.usecase.properties.GetPropertyDetailsUseCaseTest
import com.nikitosii.studyrealtorapp.domain.usecase.properties.RemovePropertyUseCaseTest
import com.nikitosii.studyrealtorapp.domain.usecase.properties.UpdatePropertyUseCaseTest
import dagger.Component
import dagger.android.AndroidInjectionModule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import javax.inject.Singleton

@OptIn(FlowPreview::class)
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
    fun inject(useCaseTest: GetLocalAgentsUseCaseTest)
    fun inject(useCaseTest: GetRecentFavoriteAgentsUseCaseTest)
    fun inject(useCaseTest: RemoveAgentUseCaseTest)
    fun inject(useCaseTest: UpdateAgentFavoriteStatusUseCaseTest)
    fun inject(useCaseTest: GetProfileFlowUseCaseTest)
    fun inject(useCaseTest: RemoveDataUseCaseTest)
    fun inject(useCaseTest: RemoveProfileDataUseCaseTest)
    fun inject(useCaseTest: UpdateProfileDataUseCaseTest)
    fun inject(useCaseTest: UpdatePropertyUseCaseTest)
    fun inject(useCaseTest: RemovePropertyUseCaseTest)
    fun inject(useCaseTest: GetPropertyDetailsUseCaseTest)
    fun inject(useCaseTest: GetLocalPropertiesUseCaseTest)
    fun inject(useCaseTest: GetLocalPropertyUseCaseTest)
    fun inject(useCaseTest: GetAllLocalPropertiesUseCaseTest)
}