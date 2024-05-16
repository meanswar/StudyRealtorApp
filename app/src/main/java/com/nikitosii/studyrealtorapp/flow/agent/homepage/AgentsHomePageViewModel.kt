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
import com.nikitosii.studyrealtorapp.flow.base.BaseViewModel
import javax.inject.Inject

class AgentsHomePageViewModel @Inject constructor(
    private val getAgentsFromNetworkUseCase: GetAgentsFromNetworkUseCase,
    private val getRecentFavoriteAgentsUseCase: GetRecentFavoriteAgentsUseCase,
    private val updateAgentFavoriteStatusUseCase: UpdateAgentFavoriteStatusUseCase
) : BaseViewModel() {
    val isLocalAgents = MutableLiveData(true)

    private val _agents = WorkLiveData<List<Agent>>()
    val agents: LiveData<WorkResult<List<Agent>>>
        get() = _agents

    private val locationFilter = MutableLiveData<String>()
    private val nameFilter = MutableLiveData<String>()
    private val ratingFilter = MutableLiveData<Int>()
    private val priceFilter = MutableLiveData<String>()
    private val photoFilter = MutableLiveData<Boolean>()
    private val langFilter = MutableLiveData<String>()

    val isFilterFilled = MutableLiveData(false)


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


    fun getAgents() {
        isLocalAgents.value = false
        val params = GetAgentsFromNetworkUseCase.Params.from(buildAgentRequest())
        ioToUiWorkData(
            io = { getAgentsFromNetworkUseCase.execute(params) },
            ui = { _agents.postValue(it) }
        )
    }

    fun getFavoriteAgents() {
        ioToUiWorkData(
            io = { getRecentFavoriteAgentsUseCase.execute() },
            ui = { _agents.postValue(it) }
        )
    }

    fun updateLocalAgent(agent: Agent) {
        if (isLocalAgents.value == true) {
            isLocalAgents.value = false
            val params = UpdateAgentFavoriteStatusUseCase.Params.create(agent)
            ioToUnit(io = { updateAgentFavoriteStatusUseCase.execute(params) })
        }
    }
}