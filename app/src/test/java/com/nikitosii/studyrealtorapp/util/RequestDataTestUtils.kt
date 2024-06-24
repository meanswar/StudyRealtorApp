package com.nikitosii.studyrealtorapp.util

import com.nikitosii.studyrealtorapp.core.source.db.entity.RequestDataEntity
import com.nikitosii.studyrealtorapp.util.TestConstants.ID_VALID
import com.nikitosii.studyrealtorapp.util.TestConstants.ID_VALID_FOR_REMOVING
import com.nikitosii.studyrealtorapp.util.TestConstants.ID_VALID_TEXT
import com.nikitosii.studyrealtorapp.util.TestConstants.ID_VALID_TEXT_FOR_CHANGES

object RequestDataTestUtils {
    fun getExpectedRequestData(): RequestDataEntity = RequestDataEntity(
        requestId = ID_VALID,
        propertiesIds = getPropertiesIds()
    )

    fun getPropertiesIds(): List<String> =
        listOf(ID_VALID_TEXT, ID_VALID_TEXT_FOR_CHANGES, ID_VALID_FOR_REMOVING)
}