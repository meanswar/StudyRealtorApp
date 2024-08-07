package com.nikitosii.studyrealtorapp.core.source.local.model

import android.os.Parcelable
import com.nikitosii.studyrealtorapp.core.source.net.model.data.BrandingResponseApi
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class Branding(
    val name: String? = null,
    val photo: String? = null,
    val type: String? = null,
    val accentColor: String? = null,
    val phone: String? = null,
): Parcelable {
    companion object {
        fun from(data: BrandingResponseApi?) = Branding(
            data?.name,
            data?.photo,
            data?.type,
            data?.accentColor,
            data?.phone
        )
    }
}