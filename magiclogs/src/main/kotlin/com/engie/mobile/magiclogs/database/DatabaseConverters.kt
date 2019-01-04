package com.engie.mobile.magiclogs.database

import android.arch.persistence.room.TypeConverter
import com.engie.mobile.magiclogs.domain.LogStatus
import org.threeten.bp.LocalDateTime

object DatabaseConverters {

    @TypeConverter @JvmStatic
    fun decodeDateTime(date: String): LocalDateTime = LocalDateTime.parse(date)

    @TypeConverter @JvmStatic
    fun encodeDateTime(date: LocalDateTime) = date.toString()

    @TypeConverter
    @JvmStatic
    fun decodeLogStatus(logStatus: String) = LogStatus.valueOf(logStatus)

    @TypeConverter
    @JvmStatic
    fun encodeLogStatus(logStatus: LogStatus) = logStatus.name
}