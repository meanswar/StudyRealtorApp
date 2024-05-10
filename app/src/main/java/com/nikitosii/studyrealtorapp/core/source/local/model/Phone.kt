package com.nikitosii.studyrealtorapp.core.source.local.model

import android.os.Parcelable
import com.nikitosii.studyrealtorapp.core.source.net.model.phone.PhoneResponseApi
import kotlinx.parcelize.Parcelize

@Parcelize
data class Phone(
    val ext: String?,
    val number: String?,
    val primary: Boolean?,
    val trackable: Boolean?,
    val type: String?
): Parcelable {
    companion object {
        fun from(data: PhoneResponseApi): Phone = Phone(
            ext = data.ext,
            number = data.number,
            primary = data.primary,
            trackable = data.trackable,
            type = data.type
        )
    }
}