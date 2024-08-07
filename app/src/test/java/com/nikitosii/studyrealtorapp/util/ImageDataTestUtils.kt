package com.nikitosii.studyrealtorapp.util

import com.nikitosii.studyrealtorapp.core.source.db.entity.ImageDataEntity
import com.nikitosii.studyrealtorapp.core.source.local.model.image.ImageData
import com.nikitosii.studyrealtorapp.util.TestConstants.ID_VALID_TEXT
import com.nikitosii.studyrealtorapp.util.TestConstants.PHOTO_VALID

object ImageDataTestUtils {
    fun getExpectedLocalImageData(): ImageDataEntity = ImageDataEntity.from(getExpectedImageData())

    fun getExpectedImageData(): ImageData = ImageData(
        request = ID_VALID_TEXT,
        url = PHOTO_VALID
    )
}