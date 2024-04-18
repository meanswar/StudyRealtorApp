package com.nikitosii.studyrealtorapp.core.source.db.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.nikitosii.studyrealtorapp.core.source.local.model.Location

class LocationTypeConverter : BaseTypeConverter<Location>() {

    @TypeConverter
    override fun fromData(data: Location?): String? {
        return Gson().toJson(data)
    }

    @TypeConverter
    override fun toData(json: String): Location? {
        return Gson().fromJson(json, object : TypeToken<Location?>() {}.type)
    }
}