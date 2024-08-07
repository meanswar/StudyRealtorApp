package com.nikitosii.studyrealtorapp.flow.main

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import com.nikitosii.studyrealtorapp.BuildConfig
import com.nikitosii.studyrealtorapp.R
import com.nikitosii.studyrealtorapp.databinding.ActivityMainBinding
import com.nikitosii.studyrealtorapp.flow.base.InjectableActivity
import com.nikitosii.studyrealtorapp.util.annotation.RequiresViewModel
import com.nikitosii.studyrealtorapp.util.ext.hasPushNotificationPermission
import com.nikitosii.studyrealtorapp.util.ext.hide
import com.nikitosii.studyrealtorapp.util.ext.requestNotificationPermission
import com.nikitosii.studyrealtorapp.util.ext.show
import com.nikitosii.studyrealtorapp.util.ext.start
import np.com.susanthapa.curved_bottom_navigation.CbnMenuItem

@RequiresViewModel(MainViewModel::class)
class MainActivity : InjectableActivity<ActivityMainBinding, MainViewModel>(
    { ActivityMainBinding.inflate(it) },
    navControllerId = R.id.navFragment
) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
        // TODO checking permission has been disabled for the time of making ui tests
//        checkNotificationPermission()

        if(BuildConfig.DEBUG){
            window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
    }

    private fun initViews() {
        with(binding) {
            bottomNavigation.setMenuItems(getMenuItems())

            navController?.let {
                bottomNavigation.setupWithNavController(it)
                it.addOnDestinationChangedListener { _, destination, _ ->
                    val destinationId = destination.id
                    tryHideBottomBar(destinationId)
                }
            }

            bottomNavigation.setOnMenuItemClickListener { item, i ->
                Handler(Looper.getMainLooper()).postDelayed({
                    navController?.navigate(item.destinationId)
                }, 250)
            }
        }
    }

    private fun getMenuItems(): Array<CbnMenuItem> {
        return arrayOf(
            CbnMenuItem(
                R.drawable.avd_menu_property_not_active,
                R.drawable.avd_menu_property_active,
                R.id.dashboardFragment
            ),
            CbnMenuItem(
                R.drawable.avd_menu_agent_not_active,
                R.drawable.avd_menu_agent_active,
                R.id.agentsFragment
            ),
            CbnMenuItem(
                R.drawable.avd_menu_profile_not_active,
                R.drawable.avd_menu_profile_active,
                R.id.profileViewPagerFragment
            ),
        )
    }


    private fun tryHideBottomBar(destinationId: Int) {
        binding.run {
            when (destinationId) {
                R.id.dashboardFragment,
                R.id.profileViewPagerFragment,
                R.id.agentsFragment -> bottomNavigation.show(true)
                else -> bottomNavigation.hide()
            }
        }
    }

    private fun checkNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            if (!hasPushNotificationPermission())
                requestNotificationPermission()
    }

    companion object {
        fun start(context: Context?, clearTop: Boolean = true) {
            context.start(MainActivity::class, clearTop)
        }
    }
}