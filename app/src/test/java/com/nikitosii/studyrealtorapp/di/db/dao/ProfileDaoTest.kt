package com.nikitosii.studyrealtorapp.di.db.dao

import androidx.lifecycle.MutableLiveData
import com.nikitosii.studyrealtorapp.core.source.db.dao.ProfileDao
import com.nikitosii.studyrealtorapp.core.source.db.entity.ProfileEntity

object ProfileDaoTest : ProfileDao {

    private val profile = MutableLiveData<ProfileEntity>()

    override fun insertProfile(entity: ProfileEntity) {
        profile.postValue(entity)
    }

    override fun getProfile(): ProfileEntity? = profile.value

    override fun deleteProfile() {
        profile.value = null
    }
}