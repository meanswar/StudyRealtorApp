package com.nikitosii.studyrealtorapp.core.source.db.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.nikitosii.studyrealtorapp.core.source.local.model.Address
import com.nikitosii.studyrealtorapp.core.source.local.model.agent.SalePrice

class SalePriceTypeConverter : BaseTypeConverter<SalePrice>() {

    @TypeConverter
    override fun fromData(data: SalePrice?): String? {
        return Gson().toJson(data)
    }

    @TypeConverter
    override fun toData(json: String?): SalePrice? {
        val type = object : TypeToken<SalePrice?>() {}.type
        return Gson().fromJson(json, type)
    }
}