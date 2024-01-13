package com.studyrealtorapp.util.link

object DynamicLink {

    val deepLinkNavigation = DeepLinkNavigation()

    fun clear() {
        deepLinkNavigation.let {
            it.navigationId = null
            it.arguments = null
            it.url = null
        }
    }
}