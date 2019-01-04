package com.engie.sample.magiclogs.sample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.engie.mobile.magiclogs.MagicLogs
import com.engie.mobile.magiclogs.MagicLogsOptions
import com.engie.mobile.magiclogs.MagicLogsTree
import com.engie.mobile.magiclogs.api.LogsAccessTokenProvider
import com.jakewharton.threetenabp.AndroidThreeTen
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber

/**
 * This sample activity, which show how you should use MagicLogs library.
 * Parameters used in library:
 * @context: Context - application context (required)
 * @user: String - application user (required)
 * @logsTokenProvider: LogsTokenProvider - provider of logs, should return token, which is String (required)
 * @apiUrl: String - base url, which app uses to connect with API, in BuildConfig of app (required)
 * @appVersionName: String - version of mobile app, in BuildConfig of app (required)
 * @options: Int - flags: OPTION_SERVER_LOGS allows to send log on web (endpoint: https://some-server.com/api/Sync/MobileDiagnostic)
 *                          OPTION_FILE_LOGS allows to save log in storage (path: .android/sample/Logs/crash$datetime.txt or .android/sample/Logs/errors.txt)
 * In gradle add implementation of Timber and AndroidThreeTen.
 */

class MainActivity : AppCompatActivity() {
    private val logsAccessTokenProvider: LogsAccessTokenProvider
            get() = LogsAccessTokenProvider { "set token without 'Bearer' here" }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //add Timber implementation in gradle and plant Timber tree with MagicLogs
        Timber.plant(MagicLogsTree(applicationContext))
        //add AndroidThreeTen implementation in gradle and init it
        AndroidThreeTen.init(this)

        //create builder of MagicLogs and set properties
        val magiclogs = MagicLogs.Builder()
                .context(applicationContext)
                .user("sample.user@sample.com")
                .apiUrl(BuildConfig.API_URL)
                .appVersionName(BuildConfig.APP_VERSION)
                .logsTokenProvider(logsAccessTokenProvider)
                .options(MagicLogsOptions.OPTION_SERVER_LOGS or MagicLogsOptions.OPTION_FILE_LOGS)
                .build()

        //init MagicLogs
        magiclogs.init()

        create_error.setOnClickListener { Timber.e(RuntimeException("FatalException")) }
        create_crash.setOnClickListener { throw RuntimeException() }
    }
}
