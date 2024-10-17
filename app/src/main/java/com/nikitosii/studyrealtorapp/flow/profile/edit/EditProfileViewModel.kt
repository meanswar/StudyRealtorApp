package com.nikitosii.studyrealtorapp.flow.profile.edit

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nikitosii.studyrealtorapp.core.domain.WorkLiveData
import com.nikitosii.studyrealtorapp.core.domain.WorkResult
import com.nikitosii.studyrealtorapp.core.domain.useCase.profile.UpdateProfileUseCase
import com.nikitosii.studyrealtorapp.core.source.local.model.profile.Profile
import com.nikitosii.studyrealtorapp.di.modules.AppModule
import com.nikitosii.studyrealtorapp.flow.base.BaseViewModel
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject
import javax.inject.Named

class EditProfileViewModel @Inject constructor(
    private val updateProfileUseCase: UpdateProfileUseCase,
    @Named(AppModule.IO_DISPATCHER) ioDispatcher: CoroutineDispatcher,
    @Named(AppModule.MAIN_DISPATCHER) uiDispatcher: CoroutineDispatcher
) : BaseViewModel(ioDispatcher = ioDispatcher, uiDispatcher = uiDispatcher) {

    val profileName = MutableLiveData<String>()
    val profileLastName = MutableLiveData<String>()
    val profileEmail = MutableLiveData<String>()
    val profilePhone = MutableLiveData<String>()
    val profilePhoto = MutableLiveData<Uri>()

    private val _updateProfileStatus = WorkLiveData<Unit>()
    val updateProfileStatus: LiveData<WorkResult<Unit>>
        get() = _updateProfileStatus

    private fun buildProfile(): Profile {
        return Profile(
            name = profileName.value ?: "",
            surname = profileLastName.value ?: "",
            email = profileEmail.value ?: "",
            phone = profilePhone.value ?: "",
            photo = profilePhoto.value
        )
    }

    fun setProfileData(data: Profile) {
        profileName.value = data.name ?: ""
        profileLastName.value = data.surname ?: ""
        profileEmail.value = data.email ?: ""
        profilePhone.value = data.phone ?: ""
        profilePhoto.value = data.photo ?: return
    }

    fun updateProfile() {
        val params = UpdateProfileUseCase.Params.create(buildProfile())
        ioToUiWorkData(
            io = { updateProfileUseCase.execute(params) },
            ui = { _updateProfileStatus.postValue(it) }
        )
    }
}