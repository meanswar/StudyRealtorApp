package com.nikitosii.studyrealtorapp.core.source.repository

import com.nikitosii.studyrealtorapp.core.source.local.model.profile.Profile
import com.nikitosii.studyrealtorapp.util.Flow

interface ProfileRepo {

    fun getProfile(): Flow<Profile>

    suspend fun refresh()

    suspend fun updateProfile(profile: Profile)

    suspend fun removeProfileData()
}