package com.nikitosii.studyrealtorapp.core.source.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nikitosii.studyrealtorapp.core.source.db.RealtorDataBase
import com.nikitosii.studyrealtorapp.core.source.db.entity.AgentEntity

@Dao
interface AgentDao {

    @Query("SELECT * FROM ${RealtorDataBase.DATABASE_TABLE_AGENT} where id in (:id)")
    fun getLocalAgents(id: List<String>): List<AgentEntity>

    @Query("SELECT * FROM ${RealtorDataBase.DATABASE_TABLE_AGENT} where favorite = 1")
    fun getFavoriteAgents(): List<AgentEntity>

    @Query("SELECT * FROM ${RealtorDataBase.DATABASE_TABLE_AGENT} where favorite = 1 & id in (:ids)")
    fun getFavoriteAgentsListFromList(ids: List<String>): List<AgentEntity>

    @Query("SELECT * FROM ${RealtorDataBase.DATABASE_TABLE_AGENT} where favorite = 1 order by id desc limit 3")
    fun getRecentFavoriteAgents(): List<AgentEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAgents(list: List<AgentEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAgent(agent: AgentEntity)

    @Query("DELETE FROM ${RealtorDataBase.DATABASE_TABLE_AGENT}")
    fun deleteAllAgents()

    @Query("DELETE FROM ${RealtorDataBase.DATABASE_TABLE_AGENT} where id = :id")
    fun deleteAgent(id: String)
}