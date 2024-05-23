package com.nikitosii.studyrealtorapp.core.source.db.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ListOfStringTypeConverter : BaseTypeConverter<List<String>>() {

    @TypeConverter
    override fun fromData(data: List<String>?): String? {
        return Gson().toJson(data)
    }

    @TypeConverter
    override fun toData(json: String?): List<String>? {
        return Gson().fromJson(json, object : TypeToken<List<String>>() {}.type)
    }
}