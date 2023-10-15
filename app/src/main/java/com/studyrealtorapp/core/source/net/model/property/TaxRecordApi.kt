package com.studyrealtorapp.core.source.net.model.property

import com.google.gson.annotations.SerializedName

data class TaxRecordApi(
    @SerializedName("public_record_id")
    val publicRecordId: String? = null
)