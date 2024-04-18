package com.nikitosii.studyrealtorapp.core.source.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nikitosii.studyrealtorapp.core.source.db.RealtorDataBase
import com.nikitosii.studyrealtorapp.core.source.local.model.request.SalesRequest

@Entity(tableName = RealtorDataBase.DATABASE_TABLE_SALE_PROPERTIES_SEARCH)
data class SalePropertiesSearchEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val query: SalesRequest,
    val result: List<PropertyEntity>
) {

    companion object {
        fun from(data: SalesRequest, result: List<PropertyEntity>): SalePropertiesSearchEntity =
            SalePropertiesSearchEntity(query = data, result = result)
    }
}