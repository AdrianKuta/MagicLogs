package com.engie.mobile.magiclogs

import android.content.Context
import android.util.Log
import com.engie.mobile.magiclogs.utils.FileLogsUtils
import com.engie.mobile.magiclogs.utils.ServerLogsUtils
import timber.log.Timber

class MagicLogsTree(context: Context) : Timber.Tree() {

    private val fileLogsUtils = FileLogsUtils(context)
    private val serverLogsUtils = ServerLogsUtils()

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        if (t == null)
            return

        when (priority) {
            Log.ERROR -> {
                if (MagicLogsOptions.options and MagicLogsOptions.OPTION_SERVER_LOGS != 0) {
                    serverLogsUtils.sendLogToServer(t)
                }
                if (MagicLogsOptions.options and MagicLogsOptions.OPTION_FILE_LOGS != 0) {
                    fileLogsUtils.saveLogToFile(t, false)
                }
            }
        }
    }
}