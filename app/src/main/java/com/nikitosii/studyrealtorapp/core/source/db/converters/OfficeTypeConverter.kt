package com.nikitosii.studyrealtorapp.core.source.db.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.nikitosii.studyrealtorapp.core.source.local.model.Address
import com.nikitosii.studyrealtorapp.core.source.local.model.Community
import com.nikitosii.studyrealtorapp.core.source.local.model.Office

class OfficeTypeConverter: BaseTypeConverter<Office>() {

    @TypeConverter
    override fun fromData(data: Office?): String? {
        return Gson().toJson(data)
    }

    @TypeConverter
    override fun toData(json: String?): Office? {
        val type = object : TypeToken<Office>() {}.type
        return Gson().fromJson(json, type)
    }
}