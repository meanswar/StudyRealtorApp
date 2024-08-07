package com.nikitosii.studyrealtorapp.core.source.db.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.nikitosii.studyrealtorapp.core.source.local.model.HouseType
import com.nikitosii.studyrealtorapp.core.source.local.model.request.RequestType

@androidx.room.TypeConverters
class HouseTypeConverter : BaseTypeConverter<List<HouseType>>() {
    @TypeConverter
    override fun fromData(data: List<HouseType>?): String? {
        return Gson().toJson(data)
    }

    @TypeConverter
    override fun toData(json: String?): List<HouseType>? {
        return Gson().fromJson(json, object : TypeToken<List<HouseType>>() {}.type)
    }
}