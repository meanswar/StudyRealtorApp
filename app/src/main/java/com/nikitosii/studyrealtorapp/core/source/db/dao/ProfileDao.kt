package com.nikitosii.studyrealtorapp.core.source.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nikitosii.studyrealtorapp.core.source.db.RealtorDataBase
import com.nikitosii.studyrealtorapp.core.source.db.entity.ProfileEntity

@Dao
interface ProfileDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProfile(profileEntity: ProfileEntity)

    @Query("SELECT * FROM ${RealtorDataBase.DATABASE_TABLE_PROFILE}")
    fun getProfile(): ProfileEntity?

    @Query("DELETE FROM ${RealtorDataBase.DATABASE_TABLE_PROFILE}")
    fun deleteProfile()
}