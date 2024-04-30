package com.nikitosii.studyrealtorapp.core.source.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.nikitosii.studyrealtorapp.core.source.db.converters.BooleanTypeConverter
import com.nikitosii.studyrealtorapp.core.source.db.converters.BrandingTypeConverter
import com.nikitosii.studyrealtorapp.core.source.db.converters.CommunityTypeConverter
import com.nikitosii.studyrealtorapp.core.source.db.converters.DescriptionTypeConverter
import com.nikitosii.studyrealtorapp.core.source.db.converters.FlagsTypeConverter
import com.nikitosii.studyrealtorapp.core.source.db.converters.HouseDescriptionTypeConverter
import com.nikitosii.studyrealtorapp.core.source.db.converters.LeadAttributesTypeConverter
import com.nikitosii.studyrealtorapp.core.source.db.converters.ListOfStringTypeConverter
import com.nikitosii.studyrealtorapp.core.source.db.converters.LocationTypeConverter
import com.nikitosii.studyrealtorapp.core.source.db.converters.ObjectTypeConverter
import com.nikitosii.studyrealtorapp.core.source.db.converters.OtherTypeConverter
import com.nikitosii.studyrealtorapp.core.source.db.converters.PhotoTypeConverter
import com.nikitosii.studyrealtorapp.core.source.db.converters.PhotosTypeConverter
import com.nikitosii.studyrealtorapp.core.source.db.converters.ProductTypeConverter
import com.nikitosii.studyrealtorapp.core.source.db.converters.SourceTypeConverter
import com.nikitosii.studyrealtorapp.core.source.db.converters.TaxRecordTypeConverter
import com.nikitosii.studyrealtorapp.core.source.local.model.Branding
import com.nikitosii.studyrealtorapp.core.source.local.model.Community
import com.nikitosii.studyrealtorapp.core.source.local.model.Description
import com.nikitosii.studyrealtorapp.core.source.local.model.Flags
import com.nikitosii.studyrealtorapp.core.source.local.model.HouseDescription
import com.nikitosii.studyrealtorapp.core.source.local.model.LeadAttributes
import com.nikitosii.studyrealtorapp.core.source.local.model.Location
import com.nikitosii.studyrealtorapp.core.source.local.model.Other
import com.nikitosii.studyrealtorapp.core.source.local.model.Photo
import com.nikitosii.studyrealtorapp.core.source.local.model.Product
import com.nikitosii.studyrealtorapp.core.source.local.model.Property
import com.nikitosii.studyrealtorapp.core.source.local.model.Source
import com.nikitosii.studyrealtorapp.core.source.local.model.TaxRecord
import com.nikitosii.studyrealtorapp.core.source.local.model.VrTour

@Entity(tableName = "property")
class PropertyEntity(
    val branding: List<Branding>?,
    val comingSoonDate: String?,
    val description: Description?,
    val flags: Flags?,
    val lastUpdateDate: String?,
    val listDate: String?,
    val listPrice: Int?,
    val location: Location?,
    val photos: List<Photo>?,
    val priceReducedAmount: String?,
    val primaryPhoto: Photo,
    @PrimaryKey val propertyId: String,
    val status: String?,
    val tags: List<String>?,
    val virtualTours: List<VrTour>?

) {
    companion object {
        fun from(data: Property): PropertyEntity = PropertyEntity(
            data.branding,
            data.comingSoonDate,
            data.description,
            data.flags,
            data.lastUpdateDate,
            data.listDate,
            data.listPrice,
            data.location,
            data.photos,
            data.priceReducedAmount,
            data.primaryPhoto,
            data.propertyId ?: "",
            data.status,
            data.tags,
            data.virtualTours
        )
    }
}