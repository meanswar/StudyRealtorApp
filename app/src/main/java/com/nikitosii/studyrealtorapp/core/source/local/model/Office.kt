package com.nikitosii.studyrealtorapp.core.source.local.model

import android.os.Parcelable
import com.nikitosii.studyrealtorapp.core.source.net.model.office.OfficeResponseApi
import kotlinx.parcelize.Parcelize

@Parcelize
data class Office(
    val email: String?,
    val id: String?,
    val href: String?,
    val mls_set: String?,
    val name: String?,
    val phones: List<Phone>
): Parcelable {
    companion object {
        fun from(api: OfficeResponseApi?) = Office(
            email = api?.email,
            id = api?.fulfillment_id,
            href = api?.href,
            mls_set = api?.mls_set,
            name = api?.name,
            phones = api?.phones?.map { Phone.from(it) } ?: listOf()
        )
    }
}