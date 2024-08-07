package com.nikitosii.studyrealtorapp.flow.profile.edit

import android.net.Uri
import android.os.Bundle
import android.transition.TransitionInflater
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.google.android.material.transition.MaterialElevationScale
import com.google.android.material.transition.MaterialFadeThrough
import com.nikitosii.studyrealtorapp.R
import com.nikitosii.studyrealtorapp.core.domain.Status
import com.nikitosii.studyrealtorapp.core.domain.WorkResult
import com.nikitosii.studyrealtorapp.databinding.FragmentEditProfileBinding
import com.nikitosii.studyrealtorapp.flow.base.BaseFragment
import com.nikitosii.studyrealtorapp.util.Constants
import com.nikitosii.studyrealtorapp.util.annotation.RequiresViewModel
import com.nikitosii.studyrealtorapp.util.ext.glideImage
import com.nikitosii.studyrealtorapp.util.ext.onClick
import com.nikitosii.studyrealtorapp.util.ext.onTextChanged
import timber.log.Timber


@RequiresViewModel(EditProfileViewModel::class)
class EditProfileFragment : BaseFragment<FragmentEditProfileBinding, EditProfileViewModel>(
    { FragmentEditProfileBinding.bind(it) }, R.layout.fragment_edit_profile
) {

    private val args: EditProfileFragmentArgs by navArgs()
    lateinit var registerActivity: ActivityResultLauncher<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        exitTransition = MaterialFadeThrough().apply {
            duration = Constants.TRANSITION_DURATION
        }
        reenterTransition = MaterialElevationScale(true).apply {
            duration = Constants.TRANSITION_DURATION
        }

        registerActivity = registerForActivityResult<String, Uri>(
            ActivityResultContracts.GetContent()
        ) { uri ->
            glideImage(uri, binding.ivProfile, R.drawable.ic_user_profile)
            viewModel.profilePhoto.postValue(uri)
        }
    }

    override fun initViews() {
        val profile = args.profile
        with(binding) {
            etName.setText(profile.name)
            etPhone.setText(profile.phone)
            etEmail.setText(profile.email)
            etLastName.setText(profile.surname)
            glideImage(profile.photo, ivProfile, R.drawable.ic_user_profile)
        }

        viewModel.setProfileData(profile)
        onClick()
    }

    private fun onClick() {
        with(binding) {
            cvChangePhoto.onClick { openGallery() }
            btnUpdate.onClick { viewModel.updateProfile() }
            etName.onTextChanged { viewModel.profileName.postValue(it) }
            etLastName.onTextChanged { viewModel.profileLastName.postValue(it) }
            etEmail.onTextChanged { viewModel.profileEmail.postValue(it) }
            etPhone.onTextChanged { viewModel.profilePhone.postValue(it) }
        }
    }

    override fun subscribe() {
        with(viewModel) {
            updateProfileStatus.observe(viewLifecycleOwner, updateProfileStatusObserver)
        }
    }

    private val updateProfileStatusObserver: Observer<WorkResult<Unit>> = Observer {
        when (it.status) {
            Status.SUCCESS -> navController.navigateUp()
            Status.LOADING -> Timber.i("updating profile")
            Status.ERROR -> handleException(it.exception) { openError() }
        }
    }

    private fun openGallery() {
        registerActivity.launch("image/*")
    }
}