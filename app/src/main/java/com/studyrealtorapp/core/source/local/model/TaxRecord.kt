package com.studyrealtorapp.core.source.local.model

import com.studyrealtorapp.core.source.net.model.property.TaxRecordResponseApi

data class TaxRecord(
    val publicRecordId: String? = null
) {
    companion object {
        fun from(data: TaxRecordResponseApi?): TaxRecord = TaxRecord(data?.publicRecordId)
    }
}