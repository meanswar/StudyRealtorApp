package com.nikitosii.studyrealtorapp.core.source.db.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.nikitosii.studyrealtorapp.core.source.local.model.Community

class CommunityTypeConverter: BaseTypeConverter<Community>() {

    @TypeConverter
    override fun fromData(data: Community?): String? {
        return Gson().toJson(data)
    }

    @TypeConverter
    override fun toData(json: String?): Community? {
        val type = object : TypeToken<Community>() {}.type
        return Gson().fromJson(json, type)
    }
}