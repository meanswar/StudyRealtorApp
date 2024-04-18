package com.nikitosii.studyrealtorapp.core.source.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.nikitosii.studyrealtorapp.core.source.db.RealtorDataBase
import com.nikitosii.studyrealtorapp.core.source.db.entity.PropertyEntity

@Dao
interface SalePropertyDao {

    @Insert(onConflict = androidx.room.OnConflictStrategy.REPLACE)
    fun insertProperty(property: PropertyEntity)

    @Insert(onConflict = androidx.room.OnConflictStrategy.REPLACE)
    fun insertProperties(properties: List<PropertyEntity>)

    @Query("SELECT * FROM ${RealtorDataBase.DATABASE_TABLE_PROPERTY}")
    fun getProperties(): List<PropertyEntity>

    @Query("DELETE FROM ${RealtorDataBase.DATABASE_TABLE_PROPERTY}")
    fun deleteAllProperties()

}