package com.nikitosii.studyrealtorapp.core.source.db.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class IntTypeConverter {
    @TypeConverter
    fun restoreList(listOfString: String?): List<Int?>? {
        return Gson().fromJson(listOfString, object : TypeToken<List<Int?>?>() {}.type)
    }

    @TypeConverter
    fun saveListOfString(listOfString: List<Int?>?): String? {
        return Gson().toJson(listOfString)
    }
}