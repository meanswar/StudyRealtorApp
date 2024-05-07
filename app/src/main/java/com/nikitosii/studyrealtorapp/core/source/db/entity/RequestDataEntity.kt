package com.nikitosii.studyrealtorapp.core.source.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nikitosii.studyrealtorapp.core.source.db.RealtorDataBase.Companion.DATABASE_TABLE_REQUEST_DATA

@Entity(tableName = DATABASE_TABLE_REQUEST_DATA)
data class RequestDataEntity(
    @PrimaryKey val requestId: Int = 0,
    val propertiesIds: List<String>
)