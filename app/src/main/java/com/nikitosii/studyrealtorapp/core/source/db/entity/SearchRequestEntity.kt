package com.nikitosii.studyrealtorapp.core.source.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nikitosii.studyrealtorapp.core.source.db.RealtorDataBase
import com.nikitosii.studyrealtorapp.core.source.local.model.HouseType
import com.nikitosii.studyrealtorapp.core.source.local.model.request.RequestType
import com.nikitosii.studyrealtorapp.core.source.local.model.request.SearchRequest

@Entity(tableName = RealtorDataBase.DATABASE_TABLE_SEARCH_REQUEST)
data class SearchRequestEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
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
    val requestType: RequestType,
    val favorite: Boolean = false
) {
    companion object {
        fun from(data: SearchRequest) = SearchRequestEntity(
            id = data.id ?: 0,
            address = data.address,
            houses = data.houses,
            priceMin = data.priceMin,
            priceMax = data.priceMax,
            bedsMin = data.bedsMin,
            bedsMax = data.bedsMax,
            bathsMin = data.bathsMin,
            bathsMax = data.bathsMax,
            sqftMin = data.sqftMin,
            sqftMax = data.sqftMax,
            imageUrl = data.imageUrl,
            requestType = data.requestType,
            favorite = data.favorite
        )
        fun toLocal(data: SearchRequestEntity) = SearchRequest(
            id = data.id,
            address = data.address,
            houses = data.houses,
            priceMin = data.priceMin,
            priceMax = data.priceMax,
            bedsMin = data.bedsMin,
            bedsMax = data.bedsMax,
            bathsMin = data.bathsMin,
            bathsMax = data.bathsMax,
            sqftMin = data.sqftMin,
            sqftMax = data.sqftMax,
            imageUrl = data.imageUrl,
            requestType = data.requestType,
            favorite = data.favorite
        )
    }
}