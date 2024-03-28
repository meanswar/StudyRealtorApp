package com.nikitosii.studyrealtorapp.core.source.net.model.location.address

import com.google.gson.annotations.SerializedName

data class CountyResponseApi(
    @SerializedName(value = "fips_code")
    val fipsCode: String? = null,
    val name: String? = null
)