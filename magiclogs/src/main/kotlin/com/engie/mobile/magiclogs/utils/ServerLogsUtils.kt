package com.engie.mobile.magiclogs.utils

import com.engie.mobile.magiclogs.MagicLogsOptions
import com.engie.mobile.magiclogs.data.SendLogJob
import com.engie.mobile.magiclogs.domain.Log
import com.evernote.android.job.JobManager
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.io.PrintWriter
import java.io.StringWriter

class ServerLogsUtils{
    private val jobManager: JobManager by lazy { JobManager.instance() }

    fun sendLogToServer(e: Throwable) {
        val sw = StringWriter()
        e.printStackTrace(PrintWriter(sw))
        val exceptionAsString = sw.toString()

        val message = e.toString()

        sendLogToServer(message, exceptionAsString)
    }

    private fun sendLogToServer(message: String, stack: String) {
        val log = Log(message, stack, MagicLogsOptions.user)
        addToLocalDatabase(log)
        scheduleJob(log)
    }

    private fun addToLocalDatabase(log: Log) {
        val logDao = MagicLogsOptions.database.logDao()
        Completable.fromCallable { logDao.insertLog(log) }
                .subscribeOn(Schedulers.io())
                .subscribe()
    }

    private fun scheduleJob(log: Log) {
        Timber.tag("MagicLogs").i("Found log to send ${log.message}")
        val jobRequest = SendLogJob.buildJobRequest()
        val allJobsForTag = jobManager.getAllJobsForTag(jobRequest.tag)
        if (allJobsForTag.isEmpty()) {
            jobManager.schedule(jobRequest)
        } else {
            Timber.i("This job should be unique and is already scheduled!")
        }
    }
}