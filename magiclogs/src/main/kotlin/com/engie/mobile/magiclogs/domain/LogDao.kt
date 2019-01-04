package com.engie.mobile.magiclogs.domain

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import io.reactivex.Flowable
import io.reactivex.Maybe

@Dao
abstract class LogDao {

    @Insert
    abstract fun insertLog(log: Log): Long

    @Query(FIND_OLDEST_NOT_SENT_LOG)
    abstract fun findOldestNotSentLog(logStatus: LogStatus = LogStatus.NOT_SENT): Maybe<Log>

    fun findNotSentLogs(): Flowable<List<Log>> {
        return findLogsWithStatus(LogStatus.NOT_SENT)
    }

    @Query(FIND_LOG_WITH_STATUS)
    abstract fun findLogsWithStatus(logStatus: LogStatus): Flowable<List<Log>>

    @Query(UPDATE_LOG_STATUS)
    abstract fun updateLogStatus(logId: Long, logStatus: LogStatus)


    companion object {
        private const val FIND_LOG_WITH_STATUS =
                "SELECT * FROM ${Log.TABLE_NAME} WHERE ${Log.COLUMN_STATUS} = :logStatus\n"

        private const val UPDATE_LOG_STATUS = "" +
                "UPDATE ${Log.TABLE_NAME}\n" +
                "SET ${Log.COLUMN_STATUS} = :logStatus\n" +
                "WHERE ${Log.COLUMN_ID_LOG} = :logId\n"

        private const val FIND_OLDEST_NOT_SENT_LOG = FIND_LOG_WITH_STATUS +
                "ORDER BY ${Log.COLUMN_DATE}\n" +
                "LIMIT 1"
    }
}