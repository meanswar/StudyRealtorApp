package com.nikitosii.studyrealtorapp.core.source.local.model

import android.os.Parcelable
import com.nikitosii.studyrealtorapp.core.source.net.model.property.TaxRecordResponseApi
import kotlinx.parcelize.Parcelize

@Parcelize
data class TaxRecord(
    val publicRecordId: String? = null
) : Parcelable {
    companion object {
        fun from(data: TaxRecordResponseApi?): TaxRecord = TaxRecord(data?.publicRecordId)
    }
}