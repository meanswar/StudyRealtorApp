package com.nikitosii.studyrealtorapp.core.source.db.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.nikitosii.studyrealtorapp.core.source.local.model.request.SearchRequest

class SalesRequestTypeConverter: BaseTypeConverter<SearchRequest>() {

    @TypeConverter
    override fun fromData(data: SearchRequest?): String? {
        return Gson().toJson(data)
    }

    @TypeConverter
    override fun toData(json: String?): SearchRequest? {
        return Gson().fromJson(json, object : TypeToken<SearchRequest?>() {}.type)
    }
}