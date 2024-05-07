package com.nikitosii.studyrealtorapp.core.source.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nikitosii.studyrealtorapp.core.source.db.RealtorDataBase.Companion.DATABASE_TABLE_SALE_PROPERTIES_SEARCH
import com.nikitosii.studyrealtorapp.core.source.db.entity.SalePropertiesSearchEntity
import com.nikitosii.studyrealtorapp.core.source.local.model.request.PropertyRequest

@Dao
interface SalePropertiesSearchDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(salePropertiesSearchEntity: SalePropertiesSearchEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(salePropertiesSearchEntity: List<SalePropertiesSearchEntity>)

    @Query("SELECT * FROM $DATABASE_TABLE_SALE_PROPERTIES_SEARCH")
    fun getAll(): List<SalePropertiesSearchEntity>

    @Query("SELECT * FROM $DATABASE_TABLE_SALE_PROPERTIES_SEARCH WHERE id = :id")
    fun getById(id: Int): SalePropertiesSearchEntity

    @Query("SELECT * FROM $DATABASE_TABLE_SALE_PROPERTIES_SEARCH WHERE `query` = :query")
    fun getByQuery(query: PropertyRequest): SalePropertiesSearchEntity

    @Query("SELECT `query` FROM $DATABASE_TABLE_SALE_PROPERTIES_SEARCH ORDER BY id Desc LIMIT 3")
    fun getLastSaleRequests(): List<PropertyRequest>

    @Query("SELECT `query` FROM $DATABASE_TABLE_SALE_PROPERTIES_SEARCH ORDER BY id Desc")
    fun getSaleRequests(): List<PropertyRequest>

    @Query("DELETE FROM $DATABASE_TABLE_SALE_PROPERTIES_SEARCH WHERE id = :id")
    fun deleteById(id: Int)


    @Query("DELETE FROM $DATABASE_TABLE_SALE_PROPERTIES_SEARCH")
    fun deleteAll()
}