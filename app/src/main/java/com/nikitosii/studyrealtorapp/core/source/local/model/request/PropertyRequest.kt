package com.nikitosii.studyrealtorapp.core.source.local.model.request

import android.os.Parcelable
import com.nikitosii.studyrealtorapp.core.source.local.model.HouseType
import kotlinx.parcelize.Parcelize

@Parcelize
data class PropertyRequest(
    val address: String,
    val houses: List<HouseType>,
    val priceMin: Int? = null,
    val priceMax: Int? = null,
    val bedsMin: Int? = null,
    val bedsMax: Int? = null,
    val bathsMin: Int? = null,
    val bathsMax: Int? = null,
    val sqftMin: Int? = null,
    val sqftMax: Int? = null,
    val imageUrl: String? = null,
    val requestType: String = "buy"
): Parcelable