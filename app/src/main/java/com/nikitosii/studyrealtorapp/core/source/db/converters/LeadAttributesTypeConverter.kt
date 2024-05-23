package com.nikitosii.studyrealtorapp.core.source.db.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.nikitosii.studyrealtorapp.core.source.local.model.LeadAttributes

class LeadAttributesTypeConverter : BaseTypeConverter<LeadAttributes>() {

    @TypeConverter
    override fun fromData(data: LeadAttributes?): String? {
        return Gson().toJson(data)
    }

    @TypeConverter
    override fun toData(json: String?): LeadAttributes? {
        return Gson().fromJson(json, object : TypeToken<LeadAttributes>() {}.type)
    }
}