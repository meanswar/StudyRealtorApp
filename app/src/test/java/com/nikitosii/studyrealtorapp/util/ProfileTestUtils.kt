package com.nikitosii.studyrealtorapp.util

import android.net.Uri
import com.nikitosii.studyrealtorapp.core.source.db.entity.ProfileEntity
import com.nikitosii.studyrealtorapp.util.TestConstants.ANY_TEXT
import com.nikitosii.studyrealtorapp.util.TestConstants.ID_VALID

object ProfileTestUtils {
    fun getExpectedProfile(): ProfileEntity = ProfileEntity(
        id = ID_VALID,
        name = ANY_TEXT,
        surname = ANY_TEXT,
        email = ANY_TEXT,
        phone = ANY_TEXT,
        photo = Uri.EMPTY
    )
}