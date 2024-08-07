package com.nikitosii.studyrealtorapp.core.source.db.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.nikitosii.studyrealtorapp.core.source.local.model.VrTour

class VrTourTypeConverter : BaseTypeConverter<List<VrTour>>() {

    @TypeConverter
    override fun fromData(data: List<VrTour>?): String? = Gson().toJson(data)


    @TypeConverter
    override fun toData(json: String?): List<VrTour>? =
        Gson().fromJson(json, object : TypeToken<List<VrTour>>() {}.type)
}