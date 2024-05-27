package com.nikitosii.studyrealtorapp.core.source.repository.impl

import com.nikitosii.studyrealtorapp.core.source.connectivity.ConnectivityProvider
import com.nikitosii.studyrealtorapp.core.source.db.dao.ProfileDao
import com.nikitosii.studyrealtorapp.core.source.local.model.profile.Profile
import com.nikitosii.studyrealtorapp.core.source.repository.ProfileRepo
import com.nikitosii.studyrealtorapp.core.source.repository.base.ChannelRecreateObserver
import com.nikitosii.studyrealtorapp.core.source.repository.base.repoChannel
import com.nikitosii.studyrealtorapp.util.Flow
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import javax.inject.Inject

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
class ProfileRepoImpl @Inject constructor(
    private val dao: ProfileDao,
    io: CoroutineDispatcher,
    connectivityProvider: ConnectivityProvider,
    recreateObserver: ChannelRecreateObserver
) : ProfileRepo {

    private val channel = repoChannel<Profile>(io, connectivityProvider, recreateObserver) {
        storageConfig { get = { Profile.from(dao.getProfile()) } }
    }

    override fun getProfile(): Flow<Profile> = channel.value.flow

    override suspend fun refresh() { channel.value.refresh() }

    override suspend fun updateProfile(profile: Profile) { dao.insertProfile(profile.toEntity()) }

    override suspend fun removeProfileData() { dao.deleteProfile() }
}