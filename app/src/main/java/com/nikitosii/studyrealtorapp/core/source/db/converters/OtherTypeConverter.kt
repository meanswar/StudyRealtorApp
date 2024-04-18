package com.nikitosii.studyrealtorapp.core.source.db.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.nikitosii.studyrealtorapp.core.source.local.model.Other

class OtherTypeConverter : BaseTypeConverter<List<Other>>() {

    @TypeConverter
    override fun fromData(data: List<Other>?): String? {
        return Gson().toJson(data)
    }

    @TypeConverter
    override fun toData(json: String): List<Other>? {
        return Gson().fromJson(json, object : TypeToken<List<Other>>() {}.type)
    }
}