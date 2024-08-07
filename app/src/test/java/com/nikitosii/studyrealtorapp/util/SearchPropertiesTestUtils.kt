package com.nikitosii.studyrealtorapp.util

import com.nikitosii.studyrealtorapp.core.source.db.entity.SearchPropertiesDataEntity
import com.nikitosii.studyrealtorapp.util.TestConstants.ANY_DIGITS

object SearchPropertiesTestUtils {
    fun getExpectedData() = listOf(
        SearchPropertiesDataEntity(
            id = ANY_DIGITS,
            query = SearchRequestTestUtils.getExpectedSearchRequest(),
            result = PropertyTestUtils.getExpectedProperties()
        )
    )
}