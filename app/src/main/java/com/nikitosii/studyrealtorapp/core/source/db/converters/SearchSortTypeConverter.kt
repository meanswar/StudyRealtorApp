package com.nikitosii.studyrealtorapp.core.source.db.converters

import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.nikitosii.studyrealtorapp.core.source.local.model.Description
import com.nikitosii.studyrealtorapp.core.source.local.model.request.SearchSortType

@TypeConverters
class SearchSortTypeConverter: BaseTypeConverter<SearchSortType>() {

    @TypeConverter
    override fun fromData(data: SearchSortType?): String? {
        return Gson().toJson(data)
    }

    @TypeConverter
    override fun toData(json: String?): SearchSortType? {
        return Gson().fromJson(json, object : TypeToken<SearchSortType>() {}.type)
    }
}