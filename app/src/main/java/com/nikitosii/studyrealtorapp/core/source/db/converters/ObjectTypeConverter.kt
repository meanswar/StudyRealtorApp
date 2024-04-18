package com.nikitosii.studyrealtorapp.core.source.db.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ObjectTypeConverter : BaseTypeConverter<List<Any>>() {

    @TypeConverter
    override fun fromData(data: List<Any>?): String? {
        return Gson().toJson(data)
    }

    @TypeConverter
    override fun toData(json: String): List<Any>? {
        return Gson().fromJson(json, object : TypeToken<List<Any>>() {}.type)
    }
}