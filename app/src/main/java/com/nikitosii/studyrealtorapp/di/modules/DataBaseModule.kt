package com.nikitosii.studyrealtorapp.di.modules

import android.content.Context
import androidx.room.Room
import com.nikitosii.studyrealtorapp.core.source.db.RealtorDataBase
import com.nikitosii.studyrealtorapp.core.source.db.dao.SalePropertiesSearchDao
import com.nikitosii.studyrealtorapp.core.source.db.dao.SalePropertyDao
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
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    @Singleton
    internal fun providesPropertyDao(db: RealtorDataBase): SalePropertyDao = db.propertyDao()

    @Provides
    @Singleton
    internal fun providesSalePropertiesSearchDao(db: RealtorDataBase): SalePropertiesSearchDao =
        db.salePropertiesSearchDao()


}