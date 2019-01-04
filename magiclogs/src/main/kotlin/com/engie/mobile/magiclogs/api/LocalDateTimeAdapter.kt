package com.engie.mobile.magiclogs.api

import com.google.gson.TypeAdapter
import com.google.gson.internal.bind.util.ISO8601Utils
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import org.threeten.bp.DateTimeUtils
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId
import timber.log.Timber
import java.text.ParsePosition

class LocalDateTimeAdapter: TypeAdapter<LocalDateTime>() {

    override fun write(out: JsonWriter, localDateTime: LocalDateTime) {
        Timber.d("TEST: $localDateTime")
        val date = DateTimeUtils.toDate(localDateTime.atZone(ZoneId.systemDefault()).toInstant())
        val formattedDate = ISO8601Utils.format(date)
        out.value(formattedDate)
    }

    override fun read(`in`: JsonReader): LocalDateTime {
        val string = `in`.nextString()
        val date = ISO8601Utils.parse(string, ParsePosition(0))
        return DateTimeUtils.toInstant(date).atZone(ZoneId.systemDefault()).toLocalDateTime()
    }
}