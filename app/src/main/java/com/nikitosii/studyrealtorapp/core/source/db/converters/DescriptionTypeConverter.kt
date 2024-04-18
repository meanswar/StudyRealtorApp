package com.nikitosii.studyrealtorapp.core.source.db.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.nikitosii.studyrealtorapp.core.source.local.model.Description

class DescriptionTypeConverter: BaseTypeConverter<Description>() {

    @TypeConverter
    override fun fromData(data: Description?): String? {
        return Gson().toJson(data)
    }

    @TypeConverter
    override fun toData(json: String): Description? {
        return Gson().fromJson(json, object : TypeToken<Description>() {}.type)
    }
}