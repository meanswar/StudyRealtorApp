package com.nikitosii.studyrealtorapp.core.source.db.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.nikitosii.studyrealtorapp.core.source.local.model.Photo

class PhotosTypeConverter : BaseTypeConverter<List<Photo>>() {

    @TypeConverter
    override fun fromData(data: List<Photo>?): String? {
        return Gson().toJson(data)
    }

    @TypeConverter
    override fun toData(json: String): List<Photo>? {
        return Gson().fromJson(json, object : TypeToken<List<Photo>>() {}.type)
    }
}