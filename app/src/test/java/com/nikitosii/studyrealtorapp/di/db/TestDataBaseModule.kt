package com.nikitosii.studyrealtorapp.di.db

import androidx.room.DatabaseConfiguration
import androidx.room.InvalidationTracker
import androidx.sqlite.db.SupportSQLiteOpenHelper
import com.nikitosii.studyrealtorapp.core.source.db.RealtorDataBase
import com.nikitosii.studyrealtorapp.core.source.db.dao.AgentDao
import com.nikitosii.studyrealtorapp.core.source.db.dao.ImageDataDao
import com.nikitosii.studyrealtorapp.core.source.db.dao.ProfileDao
import com.nikitosii.studyrealtorapp.core.source.db.dao.PropertyDao
import com.nikitosii.studyrealtorapp.core.source.db.dao.RequestDataDao
import com.nikitosii.studyrealtorapp.core.source.db.dao.SearchPropertiesDao
import com.nikitosii.studyrealtorapp.core.source.db.dao.SearchRequestDao
import com.nikitosii.studyrealtorapp.di.db.dao.AgentDaoTest
import com.nikitosii.studyrealtorapp.di.db.dao.ImageDataDaoTest
import com.nikitosii.studyrealtorapp.di.db.dao.ProfileDaoTest
import com.nikitosii.studyrealtorapp.di.db.dao.PropertyDaoTest
import com.nikitosii.studyrealtorapp.di.db.dao.RequestDataDaoTest
import com.nikitosii.studyrealtorapp.di.db.dao.SearchPropertiesDaoTest
import com.nikitosii.studyrealtorapp.di.db.dao.SearchRequestDaoTest
import dagger.Module
import dagger.Provides
import org.mockito.Mockito.mock
import javax.inject.Singleton

@Module
object TestDataBaseModule {

    @Provides
    @Singleton
    internal fun providesDatabase() = object : RealtorDataBase() {

        override fun propertyDao(): PropertyDao = PropertyDaoTest

        override fun searchPropertiesDao(): SearchPropertiesDao = SearchPropertiesDaoTest

        override fun searchRequestDao(): SearchRequestDao = SearchRequestDaoTest

        override fun requestDataDao(): RequestDataDao = RequestDataDaoTest

        override fun agentsDao(): AgentDao = AgentDaoTest

        override fun imageDataDao(): ImageDataDao = ImageDataDaoTest

        override fun profileDao(): ProfileDao = ProfileDaoTest

        override fun createOpenHelper(config: DatabaseConfiguration?): SupportSQLiteOpenHelper {
            TODO("Not yet implemented")
        }

        override fun createInvalidationTracker(): InvalidationTracker {
            return mock(InvalidationTracker::class.java)
        }

        override fun clearAllTables() {}
    }


    @Provides
    @Singleton
    internal fun provideAgentDao(db: RealtorDataBase): AgentDao = db.agentsDao()

    @Provides
    @Singleton
    internal fun provideImageDataDao(db: RealtorDataBase): ImageDataDao = db.imageDataDao()

    @Provides
    @Singleton
    internal fun provideProfileDao(db: RealtorDataBase): ProfileDao = db.profileDao()

    @Provides
    @Singleton
    internal fun providePropertyDao(db: RealtorDataBase): PropertyDao = db.propertyDao()

    @Provides
    @Singleton
    internal fun provideRequestDataDao(db: RealtorDataBase): RequestDataDao = db.requestDataDao()

    @Provides
    @Singleton
    internal fun provideSearchPropertiesDao(db: RealtorDataBase): SearchPropertiesDao = db.searchPropertiesDao()

    @Provides
    @Singleton
    internal fun provideSearchRequestDao(db: RealtorDataBase): SearchRequestDao = db.searchRequestDao()
}
