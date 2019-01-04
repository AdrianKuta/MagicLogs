package com.engie.mobile.magiclogs.data

import com.engie.mobile.magiclogs.MagicLogsOptions
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

class DataChangesProvider {
    private val logDao = MagicLogsOptions.database.logDao()

    fun dataObservable(): Observable<LogDataChanged> {
        return logDao.findNotSentLogs()
                .debounce(5, TimeUnit.SECONDS)
                .filter { logs -> logs.isNotEmpty() }
                .map { LogDataChanged }
                .toObservable()
    }
}