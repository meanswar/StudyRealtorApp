package com.nikitosii.studyrealtorapp.core.source.db.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.nikitosii.studyrealtorapp.core.source.local.model.request.PropertyRequest

class SalesRequestTypeConverter: BaseTypeConverter<PropertyRequest>() {

    @TypeConverter
    override fun fromData(data: PropertyRequest?): String? {
        return Gson().toJson(data)
    }

    @TypeConverter
    override fun toData(json: String): PropertyRequest? {
        return Gson().fromJson(json, object : TypeToken<PropertyRequest?>() {}.type)
    }
}