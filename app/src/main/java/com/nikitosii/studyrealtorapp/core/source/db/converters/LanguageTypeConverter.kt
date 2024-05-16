package com.nikitosii.studyrealtorapp.core.source.db.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.nikitosii.studyrealtorapp.core.source.local.model.Advertiser
import com.nikitosii.studyrealtorapp.core.source.local.model.agent.Language

class LanguageTypeConverter: BaseTypeConverter<List<Language>>() {

    @TypeConverter
    override fun fromData(data: List<Language>?): String? {
        return Gson().toJson(data)
    }

    @TypeConverter
    override fun toData(json: String): List<Language>? {
        return Gson().fromJson(json, object : TypeToken<List<Language>>() {}.type)
    }
}