package com.nikitosii.studyrealtorapp.core.source.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nikitosii.studyrealtorapp.core.source.db.RealtorDataBase
import com.nikitosii.studyrealtorapp.core.source.db.entity.SearchRequestEntity
import com.nikitosii.studyrealtorapp.core.source.local.model.request.RequestType

@Dao
interface SearchRequestDao {

    @Query("SELECT * FROM ${RealtorDataBase.DATABASE_TABLE_SEARCH_REQUEST} where requestType = :requestType")
    fun getSearchRequest(requestType: RequestType): List<SearchRequestEntity>

    @Query("SELECT * FROM ${RealtorDataBase.DATABASE_TABLE_SEARCH_REQUEST} where requestType = :requestType ORDER BY id Desc LIMIT 3")
    fun getRecentSearchRequests(requestType: RequestType): List<SearchRequestEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(searchRequestEntity: SearchRequestEntity): Long

    @Query("DELETE FROM ${RealtorDataBase.DATABASE_TABLE_SEARCH_REQUEST}")
    fun removeAll()

    @Query("DELETE FROM ${RealtorDataBase.DATABASE_TABLE_SEARCH_REQUEST} WHERE id = :id")
    fun remove(id: Int)

    @Query("DELETE FROM ${RealtorDataBase.DATABASE_TABLE_SEARCH_REQUEST} WHERE id != :id")
    fun removeNot(id: Int = 1)
}