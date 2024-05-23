package com.nikitosii.studyrealtorapp.core.source.db.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.nikitosii.studyrealtorapp.core.source.local.model.Photo

class PhotoTypeConverter : BaseTypeConverter<Photo>() {

    @TypeConverter
    override fun fromData(data: Photo?): String? {
        return Gson().toJson(data)
    }

    @TypeConverter
    override fun toData(json: String?): Photo? {
        return Gson().fromJson(json, object : TypeToken<Photo>() {}.type)
    }
}