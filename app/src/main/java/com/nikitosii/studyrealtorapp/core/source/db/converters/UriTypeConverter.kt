package com.nikitosii.studyrealtorapp.core.source.db.converters

import android.net.Uri
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.nikitosii.studyrealtorapp.core.source.local.model.Address

class UriTypeConverter : BaseTypeConverter<Uri>() {

    @TypeConverter
    override fun fromData(data: Uri?): String? {
        return Gson().toJson(data)
    }

    @TypeConverter
    override fun toData(json: String?): Uri? {
        val type = object : TypeToken<Uri>() {}.type
        return Gson().fromJson(json, type)
    }
}