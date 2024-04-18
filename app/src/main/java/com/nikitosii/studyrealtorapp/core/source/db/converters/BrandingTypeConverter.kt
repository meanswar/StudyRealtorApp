package com.nikitosii.studyrealtorapp.core.source.db.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.nikitosii.studyrealtorapp.core.source.local.model.Branding

class BrandingTypeConverter: BaseTypeConverter<List<Branding>>() {

    @TypeConverter
    override fun fromData(data: List<Branding>?): String? {
        return Gson().toJson(data)
    }

    @TypeConverter
    override fun toData(json: String): List<Branding>? {
        return Gson().fromJson(json, object : TypeToken<List<Branding>>() {}.type)
    }
}