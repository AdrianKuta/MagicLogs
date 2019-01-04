package com.engie.mobile.magiclogs

import android.content.Context
import com.engie.mobile.magiclogs.api.LogsAccessTokenProvider
import com.engie.mobile.magiclogs.database.MagicLogsDatabase
import com.engie.mobile.magiclogs.exceptions.MagicLogsUncaughtExceptionHandler
import com.evernote.android.job.JobManager

class MagicLogs
private constructor(private val user: String?, private val logsTokenProvider: LogsAccessTokenProvider, private val database: MagicLogsDatabase,
                    private val apiUrl: String, private val appVersionName: String, private val options: Int) {

    fun init() {
        MagicLogsOptions.setup(user, logsTokenProvider, database, apiUrl, appVersionName, options)
    }

    class Builder {
        private lateinit var context: Context
        private lateinit var logsTokenProvider: LogsAccessTokenProvider
        private lateinit var database: MagicLogsDatabase
        private lateinit var apiUrl: String
        private lateinit var appVersionName: String
        private var user: String? = ""
        private var options: Int = 0

        fun context(context: Context): Builder {
            this.context = context
            return this
        }

        fun user(user: String?): Builder {
            this.user = user
            return this
        }

        fun logsTokenProvider(logsTokenProvider: LogsAccessTokenProvider): Builder {
            this.logsTokenProvider = logsTokenProvider
            return this
        }

        fun apiUrl(apiUrl: String): Builder {
            this.apiUrl = apiUrl
            return this
        }

        fun appVersionName(appVersionName: String): Builder {
            this.appVersionName = appVersionName
            return this
        }

        fun options(options: Int): Builder {
            this.options = options
            return this
        }

        fun build(): MagicLogs {
            DevelopmentSettings.init(context)
            val defaultHandler = Thread.getDefaultUncaughtExceptionHandler()
            Thread.setDefaultUncaughtExceptionHandler(MagicLogsUncaughtExceptionHandler(defaultHandler, context))
            JobManager.create(context).addJobCreator(MagicJobCreator())
            this.database = MagicLogsDatabase.create(context)
            return MagicLogs(user, logsTokenProvider, database, apiUrl, appVersionName, options)
        }
    }
}

object MagicLogsOptions {

    val OPTION_SERVER_LOGS = 1
    val OPTION_FILE_LOGS = 2

    var user: String? = ""
    private lateinit var logsAccessTokenProvider: LogsAccessTokenProvider
    internal val accessToken: String
        get() = logsAccessTokenProvider.getAccessToken()
    internal lateinit var apiUrl: String
        private set
    internal lateinit var appVersionName: String
        private set
    internal lateinit var database: MagicLogsDatabase
        private set
    internal var options = 0
        private set

    fun setup(user: String?, logsTokenProvider: LogsAccessTokenProvider, database: MagicLogsDatabase,
              apiUrl: String, appVersionName: String, options: Int) {
        this.user = user
        this.logsAccessTokenProvider = logsTokenProvider
        this.database = database
        this.apiUrl = apiUrl
        this.appVersionName = appVersionName
        this.options = options
    }
}
