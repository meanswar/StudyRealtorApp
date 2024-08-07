package com.nikitosii.studyrealtorapp.core.source.db.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.nikitosii.studyrealtorapp.core.source.local.model.TaxRecord

class TaxRecordTypeConverter : BaseTypeConverter<TaxRecord>() {

    @TypeConverter
    override fun fromData(data: TaxRecord?): String? {
        return Gson().toJson(data)
    }

    @TypeConverter
    override fun toData(json: String?): TaxRecord? {
        return Gson().fromJson(json, object : TypeToken<TaxRecord>() {}.type)
    }
}