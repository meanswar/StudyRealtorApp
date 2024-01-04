package com.studyrealtorapp.core.source.local.model

import com.studyrealtorapp.core.source.net.model.description.DescriptionResponseApi

data class Description(
    val baths: Int? = null,
    val bathsGtr1: Int? = null,
    val bathsGtr3: Int? = null,
    val bathsFull: Int? = null,
    val bathsHalf: Int? = null,
    val beds: Int? = null,
    val garage: Int? = null,
    val lotSqft: Int? = null,
    val name: String? = null,
    val soldDate: String? = null,
    val soldPrice: Int? = null,
    val sqft: Int? = null,
    val stories: Int? = null,
    val subType: String? = null,
    val type: String? = null,
    val yearBuilt: Int? = null
) {
    companion object {
        fun from(data: DescriptionResponseApi?): Description = Description(
            baths = data?.baths,
            bathsGtr1 = data?.bathsGtr1,
            bathsGtr3 = data?.bathsGtr3,
            bathsFull = data?.bathsFull,
            bathsHalf = data?.bathsHalf,
            beds = data?.beds,
            garage = data?.garage,
            lotSqft = data?.lotSqft,
            name = data?.name,
            soldDate = data?.soldDate,
            soldPrice = data?.soldPrice,
            sqft = data?.sqft,
            stories = data?.stories,
            subType = data?.subType,
            type = data?.type,
            yearBuilt = data?.yearBuilt
        )
    }
}