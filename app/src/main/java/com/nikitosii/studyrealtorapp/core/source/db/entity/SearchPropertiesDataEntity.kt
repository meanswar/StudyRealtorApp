package com.nikitosii.studyrealtorapp.core.source.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nikitosii.studyrealtorapp.core.source.db.RealtorDataBase
import com.nikitosii.studyrealtorapp.core.source.local.model.request.SearchRequest

@Entity(tableName = RealtorDataBase.DATABASE_TABLE_SALE_PROPERTIES_SEARCH)
data class SearchPropertiesDataEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val query: SearchRequest,
    val result: List<PropertyEntity>
) {

    companion object {
        fun from(data: SearchRequest, result: List<PropertyEntity>): SearchPropertiesDataEntity =
            SearchPropertiesDataEntity(query = data, result = result)
    }
}