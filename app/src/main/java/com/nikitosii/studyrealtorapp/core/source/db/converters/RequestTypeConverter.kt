package com.nikitosii.studyrealtorapp.core.source.db.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.nikitosii.studyrealtorapp.core.source.local.model.request.RequestType

@androidx.room.TypeConverters
class RequestTypeConverter : BaseTypeConverter<RequestType>() {
    @TypeConverter
    override fun fromData(data: RequestType?): String? {
        return Gson().toJson(data)
    }

    @TypeConverter
    override fun toData(json: String): RequestType? {
        return Gson().fromJson(json, object : TypeToken<RequestType>() {}.type)
    }
}