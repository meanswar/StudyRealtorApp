package com.nikitosii.studyrealtorapp.di.modules

import android.content.Context
import androidx.room.Room
import com.nikitosii.studyrealtorapp.core.source.db.RealtorDataBase
import com.nikitosii.studyrealtorapp.core.source.db.RealtorDataBase_Migrations
import com.nikitosii.studyrealtorapp.core.source.db.dao.AgentDao
import com.nikitosii.studyrealtorapp.core.source.db.dao.ImageDataDao
import com.nikitosii.studyrealtorapp.core.source.db.dao.ProfileDao
import com.nikitosii.studyrealtorapp.core.source.db.dao.PropertyDao
import com.nikitosii.studyrealtorapp.core.source.db.dao.RequestDataDao
import com.nikitosii.studyrealtorapp.core.source.db.dao.SearchPropertiesDao
import com.nikitosii.studyrealtorapp.core.source.db.dao.SearchRequestDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object DataBaseModule {

    @Provides
    @Singleton
    internal fun providesDatabase(context: Context) = Room.databaseBuilder(
        context,
        RealtorDataBase::class.java,
        RealtorDataBase.DATABASE_NAME
    )
        .addMigrations(*RealtorDataBase_Migrations.build())
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    @Singleton
    internal fun providesPropertyDao(db: RealtorDataBase): PropertyDao = db.propertyDao()

    @Provides
    @Singleton
    internal fun providesSalePropertiesSearchDao(db: RealtorDataBase): SearchPropertiesDao =
        db.searchPropertiesDao()

    @Provides
    @Singleton
    internal fun provideRequestDataDao(db: RealtorDataBase): RequestDataDao = db.requestDataDao()

    @Provides
    @Singleton
    internal fun providesSearchRequestDao(db: RealtorDataBase): SearchRequestDao =
        db.searchRequestDao()

    @Provides
    @Singleton
    internal fun providesAgentsDao(db: RealtorDataBase): AgentDao = db.agentsDao()

    @Provides
    @Singleton
    internal fun providesImageDataDao(db: RealtorDataBase): ImageDataDao = db.imageDataDao()

    @Provides
    @Singleton
    internal fun providesProfileDao(db: RealtorDataBase): ProfileDao = db.profileDao()
}