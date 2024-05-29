package com.nikitosii.studyrealtorapp.flow.profile

import android.annotation.SuppressLint
import android.view.MenuInflater
import androidx.lifecycle.Observer
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.nikitosii.studyrealtorapp.R
import com.nikitosii.studyrealtorapp.core.domain.Status.ERROR
import com.nikitosii.studyrealtorapp.core.domain.Status.LOADING
import com.nikitosii.studyrealtorapp.core.domain.Status.SUCCESS
import com.nikitosii.studyrealtorapp.core.domain.WorkResult
import com.nikitosii.studyrealtorapp.core.source.channel.Status
import com.nikitosii.studyrealtorapp.core.source.local.model.profile.Profile
import com.nikitosii.studyrealtorapp.databinding.FragmentProfileBinding
import com.nikitosii.studyrealtorapp.flow.base.BaseFragment
import com.nikitosii.studyrealtorapp.flow.profile.agents.ProfileAgentsFragment
import com.nikitosii.studyrealtorapp.flow.profile.properties.ProfilePropertiesFragment
import com.nikitosii.studyrealtorapp.flow.profile.requests.ProfileRequestsFragment
import com.nikitosii.studyrealtorapp.util.annotation.RequiresViewModel
import com.nikitosii.studyrealtorapp.util.ext.glideImage
import com.nikitosii.studyrealtorapp.util.ext.showText
import com.nikitosii.studyrealtorapp.util.view.viewpager.ViewPagerFragmentAdapter
import timber.log.Timber

@RequiresViewModel(ProfileViewModel::class)
class ProfileViewPagerFragment : BaseFragment<FragmentProfileBinding, ProfileViewModel>(
    { FragmentProfileBinding.bind(it) }, R.layout.fragment_profile
) {

    private lateinit var viewPagerAdapter: ViewPagerFragmentAdapter

    override fun initViews() {
        with(binding) {
            viewPagerAdapter = ViewPagerFragmentAdapter(
                this@ProfileViewPagerFragment,
                listOf(
                    ProfilePropertiesFragment(),
                    ProfileRequestsFragment(),
                    ProfileAgentsFragment(),
                )
            )
            vpContent.apply {
                adapter = viewPagerAdapter
                isUserInputEnabled = true
                orientation = ViewPager2.ORIENTATION_HORIZONTAL
            }
            TabLayoutMediator(tlContent, vpContent) { tab, position ->
                tab.text = when (position) {
                    0 -> ProfilePropertiesFragment.SCREEN_TITLE
                    1 -> ProfileRequestsFragment.SCREEN_TITLE
                    2 -> ProfileAgentsFragment.SCREEN_TITLE
                    else -> throw Exception("Invalid position: $position")
                }
            }.attach()

            MenuInflater(requireContext()).inflate(R.menu.menu_profile, vmTopMenu.menu)
            vmTopMenu.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.actionEdit -> openEditProfileScreen()
                    R.id.actionDeleteProfile -> viewModel.removeProfileData()
                    R.id.actionDelete -> viewModel.removeData()
                }
                true
            }
        }
    }

    override fun subscribe() {
        with(viewModel) {
            profile.observe(viewLifecycleOwner, profileObserver)
        }
    }

    private val profileObserver: Observer<WorkResult<Status<Profile>>> = Observer {
        when (it.status) {
            SUCCESS -> it.data?.obj?.let { profile -> setProfileData(profile) }
            ERROR -> handleException(it.exception) { openError() }
            LOADING -> Timber.i("loading profile")
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setProfileData(data: Profile) {
        with(binding) {
            tvProfileName.text = "${data.name} ${data.surname}"
            tvProfileEmail.showText(data.email)
            tvProfilePhone.showText(data.phone)
            glideImage(data.photo, ivProfilePhoto, R.drawable.ic_user_profile)
        }
    }

    private fun openEditProfileScreen() {
        val profile = viewModel.profile.value?.data?.obj ?: return
        val extras = FragmentNavigatorExtras(
            binding.cvProfilePhoto to "profile.image",
            binding.tvProfileName to "profile.name",
            binding.tvProfileEmail to "profile.email",
            binding.tvProfilePhone to "profile.phone")

        ProfileViewPagerFragmentDirections.openEditProfileScreen(profile).navigate(extras)
    }
}