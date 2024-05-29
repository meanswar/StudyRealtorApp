package com.nikitosii.studyrealtorapp.core.source.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.nikitosii.studyrealtorapp.core.source.db.RealtorDataBase.Companion.DATABASE_TABLE_REQUEST_DATA
import com.nikitosii.studyrealtorapp.core.source.db.entity.RequestDataEntity


@Dao
interface RequestDataDao {
    @Query("SELECT * FROM $DATABASE_TABLE_REQUEST_DATA where requestId = :requestId")
    fun getData(requestId: Int): RequestDataEntity

    @Insert(onConflict = androidx.room.OnConflictStrategy.REPLACE)
    fun insert(requestData: RequestDataEntity)

    @Query("DELETE FROM $DATABASE_TABLE_REQUEST_DATA where requestId = :requestId")
    fun remove(requestId: Int)

    @Query("DELETE FROM $DATABASE_TABLE_REQUEST_DATA")
    fun removeAll()
}