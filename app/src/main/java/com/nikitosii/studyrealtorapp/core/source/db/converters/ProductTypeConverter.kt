package com.nikitosii.studyrealtorapp.core.source.db.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.nikitosii.studyrealtorapp.core.source.local.model.Product

class ProductTypeConverter : BaseTypeConverter<Product>() {

    @TypeConverter
    override fun fromData(data: Product?): String? {
        return Gson().toJson(data)
    }

    @TypeConverter
    override fun toData(json: String?): Product? {
        return Gson().fromJson(json, object : TypeToken<Product>() {}.type)
    }
}