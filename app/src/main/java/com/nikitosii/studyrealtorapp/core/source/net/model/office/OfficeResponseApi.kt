package com.nikitosii.studyrealtorapp.core.source.net.model.office

import com.nikitosii.studyrealtorapp.core.source.net.model.phone.PhoneResponseApi
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class OfficeResponseApi(
    val email: String?,
    val fulfillment_id: String?,
    val href: String?,
    val mls_set: String?,
    val name: String?,
    val phones: List<PhoneResponseApi>?
)