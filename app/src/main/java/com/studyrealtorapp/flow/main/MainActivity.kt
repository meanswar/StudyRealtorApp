package com.studyrealtorapp.flow.main

import android.net.Uri
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.navigation.ui.setupWithNavController
import com.android.installreferrer.api.InstallReferrerClient
import com.android.installreferrer.api.InstallReferrerStateListener
import com.android.installreferrer.api.ReferrerDetails
import com.google.android.gms.tasks.Task
import com.google.firebase.dynamiclinks.PendingDynamicLinkData
import com.google.firebase.dynamiclinks.ktx.dynamicLinks
import com.google.firebase.ktx.Firebase
import com.nikitosii.studyrealtorapp.BuildConfig
import com.nikitosii.studyrealtorapp.R
import com.nikitosii.studyrealtorapp.databinding.ActivityMainBinding
import com.studyrealtorapp.flow.base.InjectableActivity
import com.studyrealtorapp.util.annotation.RequiresViewModel
import com.studyrealtorapp.util.ext.hide
import com.studyrealtorapp.util.ext.show
import com.studyrealtorapp.util.ext.toast
import com.studyrealtorapp.util.link.DynamicLink
import timber.log.Timber

@RequiresViewModel(MainViewModel::class)
class MainActivity : InjectableActivity<ActivityMainBinding, MainViewModel>(
    { ActivityMainBinding.inflate(it) },
    navControllerId = R.id.navFragment
) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navController?.let {
            binding.bottomNavigation.setupWithNavController(it)
            it.addOnDestinationChangedListener { _, destination, _ ->
                val destinationId = destination.id
                tryHideBottomBar(destinationId)
            }
        }
        handleDeepLinkIfAvailable()
        processDeepLink()
    }


    private fun tryHideBottomBar(destinationId: Int) {
        binding.run {
            when (destinationId) {
                R.id.salesFragment,
                R.id.rentsFragment,
                R.id.agentsFragment -> bottomNavigation.show(true)

                else -> bottomNavigation.hide()
            }
        }
    }

    private fun handleDeepLinkIfAvailable() {
        intent?.let {
            parseDataFromDynamicLink(Firebase.dynamicLinks.getDynamicLink(intent))
        }
    }

    private fun processDeepLink() {
        val referrerClient: InstallReferrerClient = InstallReferrerClient.newBuilder(this).build()
        referrerClient.startConnection(object : InstallReferrerStateListener {

            override fun onInstallReferrerSetupFinished(responseCode: Int) {
                when (responseCode) {
                    InstallReferrerClient.InstallReferrerResponse.OK -> {
                        val response: ReferrerDetails = referrerClient.installReferrer
                        val referrerUrl: String = response.installReferrer
                        parseDataFromInstallReferredLink(referrerUrl)
                    }

                    InstallReferrerClient.InstallReferrerResponse.FEATURE_NOT_SUPPORTED -> {
                        // API not available on the current Play Store app.
                    }

                    InstallReferrerClient.InstallReferrerResponse.SERVICE_UNAVAILABLE -> {
                        // Connection couldn't be established.
                    }
                }
            }

            override fun onInstallReferrerServiceDisconnected() {
                // Try to restart the connection on the next request to
                // Google Play by calling the startConnection() method.
            }
        })
    }

    private fun parseDataFromDynamicLink(pendingLink: Task<PendingDynamicLinkData>) {
        pendingLink.addOnSuccessListener {
            try {
                it.link?.let { link ->
                    DynamicLink.deepLinkNavigation.arguments = bundleOf(
                        INVITE_CODE to link.getQueryParameter(INVITE_CODE)
                    )
                    val inviteCode = link.getQueryParameter(INVITE_CODE)
                    toast("invite code: $inviteCode")
                }
            } catch (e: Exception) {
                Timber.e(e)
            }
        }.addOnFailureListener { Timber.e(it) }
    }


    private fun parseDataFromInstallReferredLink(linkString: String) {
        val link = Uri.parse(linkString)
        toast("play invite link: $link")
        DynamicLink.deepLinkNavigation.arguments = bundleOf(
            INVITE_CODE to link.getQueryParameter(INVITE_CODE)
        )
        val inviteCode = link.getQueryParameter(INVITE_CODE)
        toast("play invite code: $inviteCode")
    }

    companion object {
        private const val INVITE_CODE = "inviteCode"
    }
}