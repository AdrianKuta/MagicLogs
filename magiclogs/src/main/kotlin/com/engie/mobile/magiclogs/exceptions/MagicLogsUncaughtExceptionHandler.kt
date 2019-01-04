package com.engie.mobile.magiclogs.exceptions

import android.content.Context
import com.engie.mobile.magiclogs.utils.FileLogsUtils
import com.engie.mobile.magiclogs.MagicLogsOptions
import com.engie.mobile.magiclogs.utils.ServerLogsUtils

class MagicLogsUncaughtExceptionHandler(private val defaultHandler: Thread.UncaughtExceptionHandler, context: Context) : Thread.UncaughtExceptionHandler {

    private val fileLogsUtils = FileLogsUtils(context)
    private val serverLogsUtils = ServerLogsUtils()

    override fun uncaughtException(t: Thread?, e: Throwable?) {
        e ?: return
        if (MagicLogsOptions.options and MagicLogsOptions.OPTION_SERVER_LOGS != 0) {
            serverLogsUtils.sendLogToServer(e)
        }
        if (MagicLogsOptions.options and MagicLogsOptions.OPTION_FILE_LOGS != 0) {
            fileLogsUtils.saveLogToFile(e, true)
        }
        defaultHandler.uncaughtException(t, e)
    }
}