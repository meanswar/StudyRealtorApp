package com.nikitosii.studyrealtorapp.core.source.net.exceptions

import android.content.res.Resources
import com.nikitosii.studyrealtorapp.R

class TimeoutErrorException private constructor(message: String) : BaseNetworkException(message) {
    companion object {
        fun createDefError(res: Resources) =
            TimeoutErrorException(res.getString(R.string.timeout_error))
    }
}