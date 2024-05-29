package com.nikitosii.studyrealtorapp.core.source.db.converters

import android.net.Uri
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class UriTypeConverter : BaseTypeConverter<Uri>() {

    @TypeConverter
    override fun fromData(data: Uri?): String = data.toString()

    @TypeConverter
    override fun toData(json: String?): Uri? = Uri.parse(json)
}