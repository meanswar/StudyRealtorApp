package com.nikitosii.studyrealtorapp.util.link

import android.net.Uri
import android.os.Bundle

data class DeepLinkNavigation(
    var navigationId: Int? = null,
    var arguments: Bundle? = null,
    var url: Uri? = null
)