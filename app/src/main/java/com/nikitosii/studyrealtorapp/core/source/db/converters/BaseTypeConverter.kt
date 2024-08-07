package com.nikitosii.studyrealtorapp.core.source.db.converters

import androidx.room.TypeConverter

abstract class BaseTypeConverter<T> {
    @TypeConverter
    abstract fun fromData(data: T?): String?

    @TypeConverter
    abstract fun toData(json: String?): T?
}