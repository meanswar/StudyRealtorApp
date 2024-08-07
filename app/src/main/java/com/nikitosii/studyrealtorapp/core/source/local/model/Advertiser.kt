package com.nikitosii.studyrealtorapp.core.source.local.model

import android.os.Parcelable
import com.nikitosii.studyrealtorapp.core.source.net.model.advertiser.AdvertiserResponseApi
import kotlinx.parcelize.Parcelize

@Parcelize
data class Advertiser(
    val email: String?,
    val id: String?,
    val href: String?,
    val mls_set: String?,
    val name: String?,
    val nrds_id: String?,
    val office: Office?,
    val phones: List<Phone>?
): Parcelable {
    companion object {
        fun from(api: AdvertiserResponseApi) = Advertiser(
            api.email,
            api.fulfillment_id,
            api.href,
            api.mls_set,
            api.name,
            api.nrds_id,
            Office.from(api.office),
            api.phones?.map { Phone.from(it) } ?: listOf()
        )
    }
}