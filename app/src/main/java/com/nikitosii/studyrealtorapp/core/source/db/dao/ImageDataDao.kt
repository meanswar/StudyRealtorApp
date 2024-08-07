package com.nikitosii.studyrealtorapp.core.source.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nikitosii.studyrealtorapp.core.source.db.RealtorDataBase
import com.nikitosii.studyrealtorapp.core.source.db.entity.ImageDataEntity

@Dao
interface ImageDataDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertImageData(imageData: ImageDataEntity)

    @Query("SELECT * FROM ${RealtorDataBase.DATABASE_TABLE_PHOTO} WHERE id = :id")
    fun getImage(id: String): ImageDataEntity?

    @Query("DELETE FROM ${RealtorDataBase.DATABASE_TABLE_PHOTO}")
    fun deleteAll()
}