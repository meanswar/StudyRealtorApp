package com.nikitosii.studyrealtorapp.di.db.dao

import com.nikitosii.studyrealtorapp.core.source.db.dao.AgentDao
import com.nikitosii.studyrealtorapp.core.source.db.entity.AgentEntity

object AgentDaoTest : AgentDao {

    private val agentList = mutableListOf<AgentEntity>()

    override fun getLocalAgents(id: List<String>): List<AgentEntity> =
        agentList.filter { it.id in id }


    override fun getLocalAgents(): List<AgentEntity> = agentList

    override fun getFavoriteAgents(): List<AgentEntity> = agentList.filter { it.favorite }

    override fun getFavoriteAgentsListFromList(ids: List<String>): List<AgentEntity> =
        agentList.filter { it.id in ids && it.favorite }

    override fun getRecentFavoriteAgents(): List<AgentEntity> = agentList
        .filter { it.favorite }
        .takeLast(3)

    override fun insertAgents(list: List<AgentEntity>) {
        agentList.addAll(list)
    }

    override fun insertAgent(agent: AgentEntity) {
        agentList.add(agent)
    }

    override fun deleteAllAgents() {
        agentList.removeAll { true }
    }

    override fun deleteAgent(id: String) {
        agentList.removeIf { it.id == id }
    }
}