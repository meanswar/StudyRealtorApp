package com.nikitosii.studyrealtorapp.core.source.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.nikitosii.studyrealtorapp.core.source.db.RealtorDataBase
import com.nikitosii.studyrealtorapp.core.source.db.entity.PropertyEntity

@Dao
interface PropertyDao {

    @Insert(onConflict = androidx.room.OnConflictStrategy.REPLACE)
    fun insertProperty(property: PropertyEntity): Long

    @Insert(onConflict = androidx.room.OnConflictStrategy.REPLACE)
    fun insertProperties(properties: List<PropertyEntity>): List<Long>

    @Query("SELECT * FROM ${RealtorDataBase.DATABASE_TABLE_PROPERTY}")
    fun getProperties(): List<PropertyEntity>

    @Query("SELECT * FROM ${RealtorDataBase.DATABASE_TABLE_PROPERTY} where `propertyId` IN (:ids) & `favorite` = 1")
    fun getFavoriteProperties(ids: List<String>): List<PropertyEntity>

    @Query("SELECT * FROM ${RealtorDataBase.DATABASE_TABLE_PROPERTY} where `propertyId` IN (:ids)")
    fun getLocalProperties(ids: List<String>): List<PropertyEntity>

    @Query("SELECT * FROM ${RealtorDataBase.DATABASE_TABLE_PROPERTY} where `propertyId` = :id")
    fun getLocalProperty(id: String): PropertyEntity

    @Query("DELETE FROM ${RealtorDataBase.DATABASE_TABLE_PROPERTY}")
    fun deleteAllProperties()

}