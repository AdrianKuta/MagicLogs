package com.engie.mobile.magiclogs.domain

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Build
import com.engie.mobile.magiclogs.MagicLogsOptions
import com.engie.mobile.magiclogs.domain.Log.Companion.TABLE_NAME
import org.threeten.bp.LocalDateTime

@Entity(tableName = TABLE_NAME)
data class Log(

        @ColumnInfo(name = COLUMN_MESSAGE)
        val message: String,

        @ColumnInfo(name = COLUMN_STACK)
        val stack: String,

        @ColumnInfo(name = COLUMN_USER)
        val user: String?,

        @ColumnInfo(name = COLUMN_APP_VERSION_NAME)
        val appVersionName: String = MagicLogsOptions.appVersionName,

        @ColumnInfo(name = COLUMN_ANDROID_VERSION)
        val androidVersion: String = "API ${Build.VERSION.SDK_INT}",

        @ColumnInfo(name = COLUMN_PHONE_MODEL)
        val phoneModel: String = Build.MODEL,

        @ColumnInfo(name = COLUMN_STATUS)
        val status: LogStatus = LogStatus.NOT_SENT,

        @ColumnInfo(name = COLUMN_DATE)
        val date: LocalDateTime = LocalDateTime.now(),

        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = COLUMN_ID_LOG)
        val id: Long = 0
) {
    companion object {
        const val TABLE_NAME = "log"
        const val COLUMN_ID_LOG = "id_log"
        const val COLUMN_APP_VERSION_NAME = "app_version_name"
        const val COLUMN_ANDROID_VERSION = "android_version"
        const val COLUMN_PHONE_MODEL = "phone_model"
        const val COLUMN_MESSAGE = "message"
        const val COLUMN_STACK = "stack"
        const val COLUMN_USER = "user"
        const val COLUMN_DATE = "date"
        const val COLUMN_STATUS = "status"
    }
}