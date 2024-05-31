package com.nikitosii.studyrealtorapp.flow.agent.homepage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nikitosii.studyrealtorapp.core.domain.WorkLiveData
import com.nikitosii.studyrealtorapp.core.domain.WorkResult
import com.nikitosii.studyrealtorapp.core.source.local.model.agent.Agent
import com.nikitosii.studyrealtorapp.core.source.local.model.agent.AgentRequestApi
import com.nikitosii.studyrealtorapp.core.source.useCase.agent.GetAgentsFromNetworkUseCase
import com.nikitosii.studyrealtorapp.core.source.useCase.agent.GetRecentFavoriteAgentsUseCase
import com.nikitosii.studyrealtorapp.core.source.useCase.agent.UpdateAgentFavoriteStatusUseCase
import com.nikitosii.studyrealtorapp.core.source.useCase.profile.GetProfileFlowUseCase
import com.nikitosii.studyrealtorapp.flow.base.BaseViewModel
import javax.inject.Inject

class AgentsViewModel @Inject constructor(
    getRecentFavoriteAgentsUseCase: GetRecentFavoriteAgentsUseCase,
    getProfileFlowUseCase: GetProfileFlowUseCase,
    private val updateAgentFavoriteStatusUseCase: UpdateAgentFavoriteStatusUseCase,
    private val getAgentsFromNetworkUseCase: GetAgentsFromNetworkUseCase
) : BaseViewModel() {

    val favoriteAgents = getRecentFavoriteAgentsUseCase.execute().toWorkLiveData()
    val profile = getProfileFlowUseCase.execute().toWorkLiveData()
    val isNetworkRequesting = MutableLiveData(false)

    private val _agents = WorkLiveData<List<Agent>>()
    val agentsNetwork: LiveData<WorkResult<List<Agent>>>
        get() = _agents

    val agents = MutableLiveData<List<Agent>>()

    private val locationFilter = MutableLiveData<String>()
    private val nameFilter = MutableLiveData<String>()
    private val ratingFilter = MutableLiveData<Int>()
    private val priceFilter = MutableLiveData<String>()
    private val photoFilter = MutableLiveData<Boolean>()
    private val langFilter = MutableLiveData<String>()

    val isFilterFilled = MutableLiveData(false)

    fun isNetworkRequest(): Boolean = isNetworkRequesting.value == true

    fun setLocationFilter(value: String) {
        locationFilter.value = value
        checkMainFilter()
    }

    fun setNameFilter(value: String) {
        nameFilter.value = value; checkMainFilter()
    }

    fun setRatingFilter(value: Int) {
        ratingFilter.value = value; checkMainFilter()
    }

    fun setPriceFilter(value: String) {
        priceFilter.value = value; checkMainFilter()
    }

    fun setPhotoFilter(value: Boolean) {
        photoFilter.value = value; checkMainFilter()
    }

    fun setLangFilter(value: String) {
        langFilter.value = value; checkMainFilter()
    }

    private fun checkMainFilter() {
        isFilterFilled.value = !locationFilter.value.isNullOrEmpty()
    }

    private fun buildAgentRequest(): AgentRequestApi = AgentRequestApi(
        location = locationFilter.value!!,
        agentName = nameFilter.value,
        rating = ratingFilter.value,
        price = priceFilter.value,
        photo = photoFilter.value,
        lang = langFilter.value,
        page = 1
    )

    fun updateAgentFavoriteStatus(agent: Agent) {
        val params = UpdateAgentFavoriteStatusUseCase.Params.create(agent.copy(favorite = !agent.favorite))
        ioToUnit { updateAgentFavoriteStatusUseCase.execute(params) }
    }

    fun getAgentsFromNetwork() {
        isNetworkRequesting.postValue(true)
        val params = GetAgentsFromNetworkUseCase.Params.from(buildAgentRequest())
        ioToUiWorkData(
            io = { getAgentsFromNetworkUseCase.execute(params) },
            ui = { _agents.postValue(it) })
    }
}