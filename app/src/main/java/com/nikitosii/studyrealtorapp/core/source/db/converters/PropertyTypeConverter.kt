package com.nikitosii.studyrealtorapp.core.source.db.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.nikitosii.studyrealtorapp.core.source.db.entity.PropertyEntity

class PropertyTypeConverter : BaseTypeConverter<List<PropertyEntity>>() {

    @TypeConverter
    override fun fromData(data: List<PropertyEntity>?): String? = Gson().toJson(data)

    @TypeConverter
    override fun toData(json: String): List<PropertyEntity>? = Gson().fromJson(json, object :
        TypeToken<List<PropertyEntity>>() {}.type)

}