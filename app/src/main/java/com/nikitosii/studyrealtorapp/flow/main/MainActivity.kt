package com.nikitosii.studyrealtorapp.flow.main

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.WindowManager
import android.widget.PopupMenu
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

@RequiresViewModel(MainViewModel::class)
class MainActivity : InjectableActivity<ActivityMainBinding, MainViewModel>(
    { ActivityMainBinding.inflate(it) },
    navControllerId = R.id.navFragment
) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
        checkNotificationPermission()

        if (BuildConfig.DEBUG) {
            window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
    }

    private fun initViews() {
        with(binding) {
            navController?.let {
                val popupMenu = PopupMenu(this@MainActivity, null)
                popupMenu.inflate(R.menu.menu_main)

                bottomNavigation.setupWithNavController(popupMenu.menu, it)
                it.addOnDestinationChangedListener { _, destination, _ ->
                    val destinationId = destination.id
                    tryHideBottomBar(destinationId)
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
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