package com.engie.mobile.magiclogs.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.content.Context
import com.engie.mobile.magiclogs.domain.Log
import com.engie.mobile.magiclogs.domain.LogDao

@Database(
        entities = [
            Log::class
        ],
        version = 1
)
@TypeConverters(DatabaseConverters::class)
abstract class MagicLogsDatabase : RoomDatabase() {

    abstract fun logDao(): LogDao

    companion object {
        private const val DB_NAME = "com.engie.mobile.magiclogs.db"

        fun create(context: Context): MagicLogsDatabase {
            return Room
                    .databaseBuilder(context, MagicLogsDatabase::class.java, DB_NAME)
                    .build()
        }
    }
}