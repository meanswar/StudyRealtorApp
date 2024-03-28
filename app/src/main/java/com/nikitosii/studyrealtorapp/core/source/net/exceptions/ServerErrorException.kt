package com.nikitosii.studyrealtorapp.core.source.net.exceptions

import android.content.res.Resources
import com.nikitosii.studyrealtorapp.R

class ServerErrorException private constructor(message: String) : BaseNetworkException(message) {
    companion object {
        fun createDefError(res: Resources) =
            ServerErrorException(res.getString(R.string.server_error))
    }
}