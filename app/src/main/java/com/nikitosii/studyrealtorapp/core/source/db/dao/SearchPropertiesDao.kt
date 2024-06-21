package com.nikitosii.studyrealtorapp.core.source.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nikitosii.studyrealtorapp.core.source.db.RealtorDataBase.Companion.DATABASE_TABLE_SALE_PROPERTIES_SEARCH
import com.nikitosii.studyrealtorapp.core.source.db.entity.SearchPropertiesDataEntity
import com.nikitosii.studyrealtorapp.core.source.local.model.request.SearchRequest

@Dao
interface SearchPropertiesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: SearchPropertiesDataEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(entity: List<SearchPropertiesDataEntity>)

    @Query("SELECT * FROM $DATABASE_TABLE_SALE_PROPERTIES_SEARCH")
    fun getAll(): List<SearchPropertiesDataEntity>

    @Query("SELECT * FROM $DATABASE_TABLE_SALE_PROPERTIES_SEARCH WHERE id = :id")
    fun getById(id: Int): SearchPropertiesDataEntity

    @Query("SELECT * FROM $DATABASE_TABLE_SALE_PROPERTIES_SEARCH WHERE `query` = :query")
    fun getByQuery(query: SearchRequest): SearchPropertiesDataEntity

    @Query("SELECT `query` FROM $DATABASE_TABLE_SALE_PROPERTIES_SEARCH ORDER BY id Desc LIMIT 3")
    fun getLastSaleRequests(): List<SearchRequest>

    @Query("SELECT `query` FROM $DATABASE_TABLE_SALE_PROPERTIES_SEARCH ORDER BY id Desc")
    fun getSearchRequests(): List<SearchRequest>

    @Query("DELETE FROM $DATABASE_TABLE_SALE_PROPERTIES_SEARCH WHERE id = :id")
    fun deleteById(id: Int)


    @Query("DELETE FROM $DATABASE_TABLE_SALE_PROPERTIES_SEARCH")
    fun deleteAll()
}