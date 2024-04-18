package com.nikitosii.studyrealtorapp.core.source.db.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.nikitosii.studyrealtorapp.core.source.local.model.request.SalesRequest

class SalesRequestTypeConverter: BaseTypeConverter<SalesRequest>() {

    @TypeConverter
    override fun fromData(data: SalesRequest?): String? {
        return Gson().toJson(data)
    }

    @TypeConverter
    override fun toData(json: String): SalesRequest? {
        return Gson().fromJson(json, object : TypeToken<SalesRequest?>() {}.type)
    }
}