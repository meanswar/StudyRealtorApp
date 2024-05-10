package com.nikitosii.studyrealtorapp.core.source.db.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.nikitosii.studyrealtorapp.core.source.local.model.Advertiser

class AdvertisersTypeConverter: BaseTypeConverter<List<Advertiser>>() {

    @TypeConverter
    override fun fromData(data: List<Advertiser>?): String? {
        return Gson().toJson(data)
    }

    @TypeConverter
    override fun toData(json: String): List<Advertiser>? {
        return Gson().fromJson(json, object : TypeToken<List<Advertiser>>() {}.type)
    }
}