package com.nikitosii.studyrealtorapp.core.source.db.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.nikitosii.studyrealtorapp.core.source.local.model.Address

class AddressTypeConverter : BaseTypeConverter<Address>() {

    @TypeConverter
    override fun fromData(data: Address?): String? {
        return Gson().toJson(data)
    }

    @TypeConverter
    override fun toData(json: String): Address? {
        val type = object : TypeToken<Address>() {}.type
        return Gson().fromJson(json, type)
    }
}