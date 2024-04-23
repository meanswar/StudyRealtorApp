package com.nikitosii.studyrealtorapp.core.source.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.nikitosii.studyrealtorapp.core.source.db.converters.BooleanTypeConverter
import com.nikitosii.studyrealtorapp.core.source.db.converters.BrandingTypeConverter
import com.nikitosii.studyrealtorapp.core.source.db.converters.CommunityTypeConverter
import com.nikitosii.studyrealtorapp.core.source.db.converters.DescriptionTypeConverter
import com.nikitosii.studyrealtorapp.core.source.db.converters.FlagsTypeConverter
import com.nikitosii.studyrealtorapp.core.source.db.converters.HouseDescriptionTypeConverter
import com.nikitosii.studyrealtorapp.core.source.db.converters.IntTypeConverter
import com.nikitosii.studyrealtorapp.core.source.db.converters.LeadAttributesTypeConverter
import com.nikitosii.studyrealtorapp.core.source.db.converters.ListOfStringTypeConverter
import com.nikitosii.studyrealtorapp.core.source.db.converters.LocationTypeConverter
import com.nikitosii.studyrealtorapp.core.source.db.converters.ObjectTypeConverter
import com.nikitosii.studyrealtorapp.core.source.db.converters.OtherTypeConverter
import com.nikitosii.studyrealtorapp.core.source.db.converters.PhotoTypeConverter
import com.nikitosii.studyrealtorapp.core.source.db.converters.PhotosTypeConverter
import com.nikitosii.studyrealtorapp.core.source.db.converters.ProductTypeConverter
import com.nikitosii.studyrealtorapp.core.source.db.converters.PropertyTypeConverter
import com.nikitosii.studyrealtorapp.core.source.db.converters.SalesRequestTypeConverter
import com.nikitosii.studyrealtorapp.core.source.db.converters.SourceTypeConverter
import com.nikitosii.studyrealtorapp.core.source.db.converters.TaxRecordTypeConverter
import com.nikitosii.studyrealtorapp.core.source.db.converters.VrTourTypeConverter
import com.nikitosii.studyrealtorapp.core.source.db.dao.SalePropertiesSearchDao
import com.nikitosii.studyrealtorapp.core.source.db.dao.SalePropertyDao
import com.nikitosii.studyrealtorapp.core.source.db.entity.PropertyEntity
import com.nikitosii.studyrealtorapp.core.source.db.entity.SalePropertiesSearchEntity
import com.nikitosii.studyrealtorapp.util.annotation.GenerateRoomMigrations

@Database(
    entities = [
        PropertyEntity::class,
        SalePropertiesSearchEntity::class
    ],
    version = DbConfig.VERSION
)
@GenerateRoomMigrations
@TypeConverters(
    BooleanTypeConverter::class,
    BrandingTypeConverter::class,
    CommunityTypeConverter::class,
    DescriptionTypeConverter::class,
    FlagsTypeConverter::class,
    HouseDescriptionTypeConverter::class,
    IntTypeConverter::class,
    LeadAttributesTypeConverter::class,
    ListOfStringTypeConverter::class,
    LocationTypeConverter::class,
    ObjectTypeConverter::class,
    OtherTypeConverter::class,
    PhotoTypeConverter::class,
    PhotosTypeConverter::class,
    ProductTypeConverter::class,
    PropertyTypeConverter::class,
    SalesRequestTypeConverter::class,
    SourceTypeConverter::class,
    TaxRecordTypeConverter::class,
    VrTourTypeConverter::class,
)
abstract class RealtorDataBase : RoomDatabase() {

    abstract fun propertyDao(): SalePropertyDao
    abstract fun salePropertiesSearchDao(): SalePropertiesSearchDao

    companion object {
        const val DATABASE_NAME = "studyrealtor.db"
        const val DATABASE_TABLE_PROPERTY = "property"
        const val DATABASE_TABLE_SALE_PROPERTIES_SEARCH = "sale_properties_search"
    }
}

object DbConfig {

    const val VERSION = 4
}