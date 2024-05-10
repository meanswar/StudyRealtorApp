package com.nikitosii.studyrealtorapp.core.source.net.model.advertiser

import com.nikitosii.studyrealtorapp.core.source.net.model.office.OfficeResponseApi
import com.nikitosii.studyrealtorapp.core.source.net.model.phone.PhoneResponseApi
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AdvertiserResponseApi(
    val email: String?,
    val fulfillment_id: String?,
    val href: String?,
    val mls_set: String?,
    val name: String?,
    val nrds_id: String?,
    val office: OfficeResponseApi?,
    val phones: List<PhoneResponseApi>?
)