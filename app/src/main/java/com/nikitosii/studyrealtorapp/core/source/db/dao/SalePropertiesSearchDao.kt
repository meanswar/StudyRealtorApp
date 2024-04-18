package com.nikitosii.studyrealtorapp.core.source.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nikitosii.studyrealtorapp.core.source.db.entity.SalePropertiesSearchEntity
import com.nikitosii.studyrealtorapp.core.source.local.model.request.SalesRequest

@Dao
interface SalePropertiesSearchDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(salePropertiesSearchEntity: SalePropertiesSearchEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(salePropertiesSearchEntity: List<SalePropertiesSearchEntity>)

    @Query("SELECT * FROM sale_properties_search")
    fun getAll(): List<SalePropertiesSearchEntity>

    @Query("SELECT * FROM sale_properties_search WHERE id = :id")
    fun getById(id: Int): SalePropertiesSearchEntity

    @Query("SELECT `query` FROM sale_properties_search LIMIT 3")
    fun getSaleRequests(): List<SalesRequest>

    @Query("DELETE FROM sale_properties_search WHERE id = :id")
    fun deleteById(id: Int)


    @Query("DELETE FROM sale_properties_search")
    fun deleteAll()
}