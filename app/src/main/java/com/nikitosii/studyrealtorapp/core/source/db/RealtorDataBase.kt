package com.nikitosii.studyrealtorapp.core.source.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.nikitosii.studyrealtorapp.core.source.db.converters.AddressTypeConverter
import com.nikitosii.studyrealtorapp.core.source.db.converters.AdvertisersTypeConverter
import com.nikitosii.studyrealtorapp.core.source.db.converters.BooleanTypeConverter
import com.nikitosii.studyrealtorapp.core.source.db.converters.BrandingTypeConverter
import com.nikitosii.studyrealtorapp.core.source.db.converters.CommunityTypeConverter
import com.nikitosii.studyrealtorapp.core.source.db.converters.DescriptionTypeConverter
import com.nikitosii.studyrealtorapp.core.source.db.converters.FlagsTypeConverter
import com.nikitosii.studyrealtorapp.core.source.db.converters.HouseDescriptionTypeConverter
import com.nikitosii.studyrealtorapp.core.source.db.converters.HouseTypeConverter
import com.nikitosii.studyrealtorapp.core.source.db.converters.IntTypeConverter
import com.nikitosii.studyrealtorapp.core.source.db.converters.LanguageTypeConverter
import com.nikitosii.studyrealtorapp.core.source.db.converters.LeadAttributesTypeConverter
import com.nikitosii.studyrealtorapp.core.source.db.converters.ListOfStringTypeConverter
import com.nikitosii.studyrealtorapp.core.source.db.converters.LocationTypeConverter
import com.nikitosii.studyrealtorapp.core.source.db.converters.ObjectTypeConverter
import com.nikitosii.studyrealtorapp.core.source.db.converters.OfficeTypeConverter
import com.nikitosii.studyrealtorapp.core.source.db.converters.OtherTypeConverter
import com.nikitosii.studyrealtorapp.core.source.db.converters.PhotoTypeConverter
import com.nikitosii.studyrealtorapp.core.source.db.converters.PhotosTypeConverter
import com.nikitosii.studyrealtorapp.core.source.db.converters.ProductTypeConverter
import com.nikitosii.studyrealtorapp.core.source.db.converters.PropertyTypeConverter
import com.nikitosii.studyrealtorapp.core.source.db.converters.RequestTypeConverter
import com.nikitosii.studyrealtorapp.core.source.db.converters.SalePriceTypeConverter
import com.nikitosii.studyrealtorapp.core.source.db.converters.SalesRequestTypeConverter
import com.nikitosii.studyrealtorapp.core.source.db.converters.SearchSortTypeConverter
import com.nikitosii.studyrealtorapp.core.source.db.converters.SourceTypeConverter
import com.nikitosii.studyrealtorapp.core.source.db.converters.TaxRecordTypeConverter
import com.nikitosii.studyrealtorapp.core.source.db.converters.UriTypeConverter
import com.nikitosii.studyrealtorapp.core.source.db.converters.VrTourTypeConverter
import com.nikitosii.studyrealtorapp.core.source.db.dao.AgentDao
import com.nikitosii.studyrealtorapp.core.source.db.dao.ImageDataDao
import com.nikitosii.studyrealtorapp.core.source.db.dao.ProfileDao
import com.nikitosii.studyrealtorapp.core.source.db.dao.PropertyDao
import com.nikitosii.studyrealtorapp.core.source.db.dao.RequestDataDao
import com.nikitosii.studyrealtorapp.core.source.db.dao.SearchPropertiesDao
import com.nikitosii.studyrealtorapp.core.source.db.dao.SearchRequestDao
import com.nikitosii.studyrealtorapp.core.source.db.entity.AgentEntity
import com.nikitosii.studyrealtorapp.core.source.db.entity.ImageDataEntity
import com.nikitosii.studyrealtorapp.core.source.db.entity.ProfileEntity
import com.nikitosii.studyrealtorapp.core.source.db.entity.PropertyEntity
import com.nikitosii.studyrealtorapp.core.source.db.entity.RequestDataEntity
import com.nikitosii.studyrealtorapp.core.source.db.entity.SearchPropertiesDataEntity
import com.nikitosii.studyrealtorapp.core.source.db.entity.SearchRequestEntity
import dev.matrix.roomigrant.GenerateRoomMigrations

@Database(
    entities = [
        PropertyEntity::class,
        SearchPropertiesDataEntity::class,
        RequestDataEntity::class,
        SearchRequestEntity::class,
        AgentEntity::class,
        ImageDataEntity::class,
        ProfileEntity::class
    ],
    version = DbConfig.VERSION,
)
@GenerateRoomMigrations()
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
    RequestTypeConverter::class,
    HouseTypeConverter::class,
    SearchSortTypeConverter::class,
    AdvertisersTypeConverter::class,
    LanguageTypeConverter::class,
    AddressTypeConverter::class,
    OfficeTypeConverter::class,
    SalePriceTypeConverter::class,
    UriTypeConverter::class
)
abstract class RealtorDataBase : RoomDatabase() {

    abstract fun propertyDao(): PropertyDao
    abstract fun searchPropertiesDao(): SearchPropertiesDao
    abstract fun searchRequestDao(): SearchRequestDao
    abstract fun requestDataDao(): RequestDataDao
    abstract fun agentsDao(): AgentDao

    abstract fun imageDataDao(): ImageDataDao
    abstract fun profileDao(): ProfileDao

    companion object {
        const val DATABASE_NAME = "studyrealtor.db"
        const val DATABASE_TABLE_PROPERTY = "property"
        const val DATABASE_TABLE_SALE_PROPERTIES_SEARCH = "sale_properties_search"
        const val DATABASE_TABLE_SEARCH_REQUEST = "search_request"
        const val DATABASE_TABLE_REQUEST_DATA = "request_data"
        const val DATABASE_TABLE_AGENT = "agents"
        const val DATABASE_TABLE_PHOTO = "photos"
        const val DATABASE_TABLE_PROFILE = "user_profile"
    }
}

object DbConfig {
    const val VERSION = 23
}