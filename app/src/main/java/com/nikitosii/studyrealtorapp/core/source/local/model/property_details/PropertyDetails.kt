package com.nikitosii.studyrealtorapp.core.source.local.model.property_details

import com.nikitosii.studyrealtorapp.core.source.local.model.Branding
import com.nikitosii.studyrealtorapp.core.source.local.model.Description
import com.nikitosii.studyrealtorapp.core.source.local.model.Photo
import com.nikitosii.studyrealtorapp.core.source.net.model.property.PropertyDetailsResponseApi
import com.nikitosii.studyrealtorapp.core.source.net.model.source.SourceResponseApi
import com.nikitosii.studyrealtorapp.core.source.net.model.vrtour.VrTourResponseApi

data class PropertyDetails(
    val branding: List<Branding>? = null,
    val comingSoonDate: String? = null,
    val description: Description? = null,
    val lastUpdateDate: String? = null,
    val listDate: String? = null,
    val listPrice: Int? = null,
    val listingId: String? = null,
    val location: DetailedLocation? = null,
    val photoResponseApis: List<Photo>? = null,
    val schools: EducationFacilities? = null,
    val source: SourceResponseApi? = null,
    val propertyId: String? = null,
    val status: String? = null,
    val tags: List<String>? = null,
    val virtualTours: List<VrTourResponseApi>? = null
) {
    companion object {
        fun from(source: PropertyDetailsResponseApi?) = PropertyDetails(
            source?.branding?.map { Branding.from(it) },
            source?.comingSoonDate,
            Description.from(source?.description),
            source?.lastUpdateDate,
            source?.listDate,
            source?.listPrice,
            source?.listingId,
            DetailedLocation.from(source?.location),
            source?.photoResponseApi?.map { Photo.from(it) },
            EducationFacilities.from(source?.schools),
            source?.source,
            source?.propertyId,
            source?.status,
            source?.tags,
            source?.virtualTours
        )
    }
}