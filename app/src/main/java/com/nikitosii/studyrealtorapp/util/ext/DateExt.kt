package com.nikitosii.studyrealtorapp.util.ext

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Calendar

object DateExt {
    const val SERVER_DATE_PATTERN = "yyyy-MM-dd"
    const val SERVER_YEAR_MONTH_DAY_TIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ss"
    const val SERVER_YEAR_MONTH_DAY_TIME_NANOSECOND_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS"
    const val UI_DATE_PATTERN = "dd.MM.yyy"
    const val UI_DATE_SHORT_YEAR_PATTERN = "dd.MM.yy"
    //Three spaces for the task SHEL-1109
    const val UI_DATE_PATTERN_WITH_TIME_AND_SPACE = "dd.MM.yyy   HH:mm"
    const val UI_DATE_PATTERN_WITH_TIME = "dd.MM.yyy HH:mm"
    const val UI_DATE_PATTERN_WITH_TIME_AND_COMMA = "dd.MM.yyy, HH:mm"
    const val UI_DAY_MONTH_PATTERN = "dd MMMM"
    const val UI_DAY_MONTH_YEAR_PATTERN = "dd MMMM yyyy"
    const val UI_DAY_MONTH_SHORT_YEAR_PATTERN = "dd MMM yyyy"
}

private val formatterMap = mutableMapOf<String, SimpleDateFormat>()

fun String?.toTimestamp(pattern: String = DateExt.SERVER_DATE_PATTERN): Long? {
    if (isNullOrBlank()) return null
    return getFormatterForPattern(pattern).parse(this)?.time
}

fun String?.toTimestampFromUi(pattern: String = DateExt.UI_DATE_PATTERN): Long? {
    if (isNullOrBlank()) return null
    return getFormatterForPattern(pattern).parse(this)?.time
}

fun String?.toUiTime(
    pattern: String = DateExt.SERVER_DATE_PATTERN,
    patternUI: String = DateExt.UI_DATE_PATTERN
): String? {
    if (isNullOrBlank()) return null
    return getFormatterForPattern(pattern).parse(this)?.time.toTimeString(patternUI)
}

fun Long?.toTimeString(pattern: String = DateExt.UI_DATE_PATTERN): String? {
    this ?: return null
    return getFormatterForPattern(pattern).format(this)
}

fun Long?.toServerData(pattern: String = DateExt.SERVER_DATE_PATTERN): String {
    return getFormatterForPattern(pattern).format(this)
}

fun Long.toDatePickerTimestamp() = Calendar.getInstance().run {
    timeInMillis = this@toDatePickerTimestamp
    Triple(get(Calendar.YEAR), get(Calendar.MONTH), get(Calendar.DAY_OF_MONTH))
}

@SuppressLint("SimpleDateFormat")
private fun getFormatterForPattern(pattern: String) =
    formatterMap[pattern] ?: SimpleDateFormat(pattern).also { formatterMap[pattern] = it }
