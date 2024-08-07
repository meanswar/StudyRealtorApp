package com.nikitosii.studyrealtorapp.flow.profile

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MenuInflater
import androidx.core.view.doOnPreDraw
import androidx.lifecycle.Observer
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.google.android.material.transition.MaterialElevationScale
import com.google.android.material.transition.MaterialFadeThrough
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
import com.nikitosii.studyrealtorapp.util.Constants.TRANSITION_DURATION
import com.nikitosii.studyrealtorapp.util.annotation.RequiresViewModel
import com.nikitosii.studyrealtorapp.util.ext.glideImage
import com.nikitosii.studyrealtorapp.util.ext.model.getFullName
import com.nikitosii.studyrealtorapp.util.ext.onAnimationRunning
import com.nikitosii.studyrealtorapp.util.ext.showText
import com.nikitosii.studyrealtorapp.util.ext.showWithAnimation
import com.nikitosii.studyrealtorapp.util.view.viewpager.ViewPagerFragmentAdapter
import timber.log.Timber

@RequiresViewModel(ProfileViewModel::class)
class ProfileViewPagerFragment : BaseFragment<FragmentProfileBinding, ProfileViewModel>(
    { FragmentProfileBinding.bind(it) }, R.layout.fragment_profile
) {

    private lateinit var viewPagerAdapter: ViewPagerFragmentAdapter
    private var isScrolled = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        exitTransition = MaterialFadeThrough().apply {
            duration = TRANSITION_DURATION
        }
        reenterTransition = MaterialElevationScale(true).apply {
            duration = TRANSITION_DURATION
        }
    }

    override fun initViews() {
        with(binding) {
            if (isScrolled) mlContent.transitionToEnd()
            mlContent.onAnimationRunning(
                onComplete = { isScrolled = mlContent.currentState == mlContent.endState })
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
                isUserInputEnabled = false
                orientation = ViewPager2.ORIENTATION_HORIZONTAL
            }
            TabLayoutMediator(tlContent, vpContent) { tab, position ->
                tab.text = when (position) {
                    SCREEN_PROPERTIES_POSITION -> ProfilePropertiesFragment.SCREEN_TITLE
                    SCREEN_REQUESTS_POSITION -> ProfileRequestsFragment.SCREEN_TITLE
                    SCREEN_AGENTS_POSITION -> ProfileAgentsFragment.SCREEN_TITLE
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

            clBottomContent.showWithAnimation(R.anim.slide_in_anim_top)
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
            tvProfileName.showText(data.getFullName())
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
            binding.tvProfilePhone to "profile.phone"
        )

        ProfileViewPagerFragmentDirections.openEditProfileScreen(profile).navigate(extras)
    }

    companion object {
        private const val SCREEN_PROPERTIES_POSITION = 0
        private const val SCREEN_REQUESTS_POSITION = 1
        private const val SCREEN_AGENTS_POSITION = 2
    }
}