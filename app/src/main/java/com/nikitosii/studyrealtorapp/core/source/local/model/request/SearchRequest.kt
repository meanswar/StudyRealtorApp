package com.nikitosii.studyrealtorapp.core.source.local.model.request

import android.os.Parcelable
import com.nikitosii.studyrealtorapp.core.source.db.entity.SearchRequestEntity
import com.nikitosii.studyrealtorapp.core.source.local.model.HouseType
import kotlinx.parcelize.Parcelize

@Parcelize
data class SearchRequest(
    val id: Int? = null,
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
    val favorite: Boolean = false,
    val cats: Boolean? = null,
    val dogs: Boolean? = null,
    val sort: SearchSortType? = null,
): Parcelable {
    companion object {
        fun toEntity(request: SearchRequest) = SearchRequestEntity.from(request)

        fun from(entity: SearchRequestEntity) = SearchRequest(
            id = entity.id,
            address = entity.address,
            houses = entity.houses,
            priceMin = entity.priceMin,
            priceMax = entity.priceMax,
            bedsMin = entity.bedsMin,
            bedsMax = entity.bedsMax,
            bathsMin = entity.bathsMin,
            bathsMax = entity.bathsMax,
            sqftMin = entity.sqftMin,
            sqftMax = entity.sqftMax,
            imageUrl = entity.imageUrl,
            requestType = entity.requestType,
            favorite = entity.favorite,
            cats = entity.cats,
            dogs = entity.dogs
        )

        fun emptyInstance(): SearchRequest {
            return SearchRequest(address = "", houses = emptyList(), requestType = RequestType.SALE)
        }
    }
}