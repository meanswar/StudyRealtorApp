package com.nikitosii.studyrealtorapp.core.source.net.model.agent

import com.nikitosii.studyrealtorapp.core.source.local.model.agent.SalePrice
import com.nikitosii.studyrealtorapp.core.source.net.model.location.address.AddressResponseApi
import com.nikitosii.studyrealtorapp.core.source.net.model.office.OfficeResponseApi
import com.nikitosii.studyrealtorapp.core.source.net.model.phone.PhoneResponseApi
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AgentResponseApi(
    @Json(name="full_name")
    val fullName: String?,
    @Json(name="nickname")
    val nickname: String?,
    val name: String?,
    val title: String?,
    val slogan: String?,
    @Json(name="photo_url")
    val photoUrl: String?,
    @Json(name="background_photo_url")
    val backgroundPhotoUrl: String?,
    val address: AddressResponseApi?,
    val office: OfficeResponseApi?,
    val phone: String?,
    val webUrl: String?,
    @Json(name="recently_sold_count")
    val recentlySoldCount: Int?,
    @Json(name="for_sale_price_count")
    val forSalePriceCount: Int?,
    @Json(name="min_for_sale_price")
    val minForSalePrice: Int?,
    @Json(name="max_for_sale_price")
    val maxForSalePrice: Int?,
    @Json(name="review_count")
    val reviewCount: Int?,
    @Json(name="recommendations_count")
    val recommendationsCount: Int?,
    val id: String,
    @Json(name = "for_sale_price")
    val salePrice: SalePriceResponseApi? = null
)