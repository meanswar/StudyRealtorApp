package com.nikitosii.studyrealtorapp.core.source.db.entity

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nikitosii.studyrealtorapp.core.source.db.RealtorDataBase

@Entity(tableName = RealtorDataBase.DATABASE_TABLE_PROFILE)
data class ProfileEntity(
    @PrimaryKey val id: Int = 1,
    val name: String?,
    val surname: String?,
    val email: String?,
    val phone: String?,
    val photo: Uri?
)