package com.nikitosii.studyrealtorapp.core.source.db.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.nikitosii.studyrealtorapp.core.source.local.model.Source

class SourceTypeConverter : BaseTypeConverter<Source>() {

    @TypeConverter
    override fun fromData(data: Source?): String? {
        return Gson().toJson(data)
    }

    @TypeConverter
    override fun toData(json: String?): Source? {
        return Gson().fromJson(json, object : TypeToken<Source>() {}.type)
    }
}