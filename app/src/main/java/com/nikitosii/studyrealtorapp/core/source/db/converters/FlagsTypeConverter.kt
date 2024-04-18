package com.nikitosii.studyrealtorapp.core.source.db.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.nikitosii.studyrealtorapp.core.source.local.model.Community
import com.nikitosii.studyrealtorapp.core.source.local.model.Flags

class FlagsTypeConverter : BaseTypeConverter<Flags>() {

    @TypeConverter
    override fun fromData(data: Flags?): String? {
        return Gson().toJson(data)
    }

    @TypeConverter
    override fun toData(json: String): Flags? {
        return Gson().fromJson(json, object : TypeToken<Community>() {}.type)
    }
}