package com.nikitosii.studyrealtorapp.core.source.db.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.nikitosii.studyrealtorapp.core.source.local.model.HouseDescription

class HouseDescriptionTypeConverter: BaseTypeConverter<List<HouseDescription>>() {

    @TypeConverter
    override fun fromData(data: List<HouseDescription>?): String? {
        return Gson().toJson(data)
    }

    @TypeConverter
    override fun toData(json: String): List<HouseDescription>? {
        return Gson().fromJson(json, object : TypeToken<List<HouseDescription>>() {}.type)
    }
}