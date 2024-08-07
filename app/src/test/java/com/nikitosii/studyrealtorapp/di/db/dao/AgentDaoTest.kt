package com.nikitosii.studyrealtorapp.di.db.dao

import com.nikitosii.studyrealtorapp.core.source.db.dao.AgentDao
import com.nikitosii.studyrealtorapp.core.source.db.entity.AgentEntity

object AgentDaoTest : AgentDao {

    private val data = mutableListOf<AgentEntity>()

    override fun getLocalAgents(): List<AgentEntity> = data

    override fun getFavoriteAgents(): List<AgentEntity> = data.filter { it.favorite }

    override fun getFavoriteAgentsListFromList(ids: List<String>): List<AgentEntity> =
        data.filter { it.id in ids && it.favorite }

    override fun getRecentFavoriteAgents(): List<AgentEntity> =
        data.filter { it.favorite }.takeLast(3)

    override fun insertAgents(list: List<AgentEntity>) {
        data.addAll(list)
    }

    override fun insertAgent(agent: AgentEntity) {
        if (!data.any { it.id == agent.id }) data.add(agent)
        else {
            data.removeIf { it.id == agent.id }
            data.add(agent)
        }
    }

    override fun deleteAllAgents() {
        data.clear()
    }

    override fun deleteAgent(id: String) {
        data.removeIf { it.id == id }
    }
}