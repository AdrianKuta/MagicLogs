package com.engie.mobile.magiclogs.utils

import android.content.Context
import com.engie.mobile.magiclogs.MagicLogsOptions
import timber.log.Timber
import java.io.*
import java.text.SimpleDateFormat
import java.util.*

class FileLogsUtils(private val context: Context) {

    fun saveLogToFile(e: Throwable, isCrash: Boolean) {
        val stringWriter = StringWriter()
        e.printStackTrace(PrintWriter(stringWriter))
        val stack = stringWriter.toString()

        val message = e.toString()

        saveLogToFile(message, stack, isCrash)
    }

    private fun saveLogToFile(message: String, stack: String, isCrash: Boolean) {
        val datetime = getDateAndTime()
        val dir = context.getExternalFilesDir("Logs")
        if (!dir.exists()) {
            dir.mkdirs()
        }

        val file = if (isCrash) {
            File(dir, "crash$datetime.txt")
        } else {
            File(dir, "errors.txt")
        }

        if (!file.exists()) {
            file.createNewFile()
        }

        try {
            val bufferedWriter = BufferedWriter(FileWriter(file, true))
            bufferedWriter.append("user: ${MagicLogsOptions.user}\nversion: ${MagicLogsOptions.appVersionName}\ndate: $datetime\nmessage: $message \nstack: $stack\n")
            bufferedWriter.newLine()
            bufferedWriter.close()
        } catch (e: IOException) {
            Timber.e(e)
        }
    }

    private fun getDateAndTime(): String {
        val dateFormat = SimpleDateFormat("dd-MM-yyyy HH:mm:ss")
        val datetime = Date(System.currentTimeMillis())
        return dateFormat.format(datetime)
    }
}