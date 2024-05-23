package com.nikitosii.studyrealtorapp.core.source.db.converters

import android.database.Cursor
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.nikitosii.studyrealtorapp.core.source.local.model.Address

class CursorTypeConverter : BaseTypeConverter<Cursor>() {

    @TypeConverter
    override fun fromData(data: Cursor?): String? {
        return Gson().toJson(data)
    }

    @TypeConverter
    override fun toData(json: String?): Cursor? {
        val type = object : TypeToken<Cursor>() {}.type
        return Gson().fromJson(json, type)
    }
}