package com.nikitosii.studyrealtorapp.util

import com.nikitosii.studyrealtorapp.core.source.db.entity.SearchRequestEntity
import com.nikitosii.studyrealtorapp.core.source.local.model.request.SearchRequest
import com.nikitosii.studyrealtorapp.util.TestConstants.ANY_DIGITS
import com.nikitosii.studyrealtorapp.util.TestConstants.ANY_TEXT
import com.nikitosii.studyrealtorapp.util.TestConstants.BOOLEAN_INVALID
import com.nikitosii.studyrealtorapp.util.TestConstants.BOOLEAN_VALID
import com.nikitosii.studyrealtorapp.util.TestConstants.ID_VALID
import com.nikitosii.studyrealtorapp.util.TestConstants.SEARCH_TYPE_RENT

object SearchRequestTestUtils {
    fun getExpectedSearchRequest(): SearchRequest = SearchRequest(
        id = ID_VALID,
        address = ANY_TEXT,
        houses = emptyList(),
        requestType = SEARCH_TYPE_RENT,
        priceMin = ANY_DIGITS,
        priceMax = ANY_DIGITS,
        bedsMin = ANY_DIGITS,
        bedsMax = ANY_DIGITS,
        bathsMin = ANY_DIGITS,
        bathsMax = ANY_DIGITS,
        sqftMin = ANY_DIGITS,
        sqftMax = ANY_DIGITS,
        imageUrl = ANY_TEXT,
        favorite = BOOLEAN_VALID,
        cats = BOOLEAN_INVALID,
        dogs = BOOLEAN_INVALID
    )

    fun getExpectedEntity(): SearchRequestEntity = SearchRequestEntity.from(getExpectedSearchRequest())
}