package com.nikitosii.studyrealtorapp.core.source.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nikitosii.studyrealtorapp.core.source.db.RealtorDataBase
import com.nikitosii.studyrealtorapp.core.source.local.model.image.ImageData

@Entity(tableName = RealtorDataBase.DATABASE_TABLE_PHOTO)
data class ImageDataEntity(
    @PrimaryKey val id: String,
    val url: String?
) {
    companion object {

        fun from(photo: ImageData) = ImageDataEntity(
            id = photo.request,
            url = photo.url
        )
    }
}