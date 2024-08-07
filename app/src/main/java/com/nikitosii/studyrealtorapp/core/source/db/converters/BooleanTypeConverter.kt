package com.nikitosii.studyrealtorapp.core.source.db.converters

import androidx.room.TypeConverter
import com.google.gson.reflect.TypeToken
import com.nikitosii.studyrealtorapp.util.ext.isNotNull

class BooleanTypeConverter {
    @TypeConverter
    fun fromBoolean(value: Boolean?): Int? {
        return if (value.isNotNull())
            if (value == true) 1 else  0
        else null
    }

    @TypeConverter
    fun toBoolean(value: Int?): Boolean? {
        val listType = object : TypeToken<Boolean>() {}.type
        return if (value.isNotNull()) value == 1 else null
    }
}