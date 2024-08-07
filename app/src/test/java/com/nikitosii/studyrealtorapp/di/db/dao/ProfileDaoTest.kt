package com.nikitosii.studyrealtorapp.di.db.dao

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import com.nikitosii.studyrealtorapp.core.source.db.dao.ProfileDao
import com.nikitosii.studyrealtorapp.core.source.db.entity.ProfileEntity
import com.nikitosii.studyrealtorapp.core.source.local.model.profile.Profile
import com.nikitosii.studyrealtorapp.util.TestConstants

object ProfileDaoTest : ProfileDao {

    private val profile = MutableLiveData(
        ProfileEntity(
            TestConstants.ID_VALID,
            TestConstants.ANY_TEXT,
            TestConstants.ANY_TEXT,
            TestConstants.ANY_TEXT,
            TestConstants.ANY_TEXT,
            Uri.EMPTY
        )
    )

    override fun insertProfile(entity: ProfileEntity) {
        profile.postValue(entity)
    }

    override fun getProfile(): ProfileEntity? = profile.value

    override fun deleteProfile() {
        profile.value = null
    }
}