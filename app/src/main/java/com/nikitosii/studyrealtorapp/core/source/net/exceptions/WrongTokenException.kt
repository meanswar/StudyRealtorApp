package com.nikitosii.studyrealtorapp.core.source.net.exceptions

import android.content.res.Resources
import com.nikitosii.studyrealtorapp.R

class WrongTokenException private constructor(message: String) : BaseNetworkException(message) {
    companion object {
        fun createDefError(res: Resources) =
            WrongTokenException(res.getString(R.string.wrong_token_error))
    }
}