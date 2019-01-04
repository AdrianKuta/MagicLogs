# MagicLogs

The Android library writen in Kotlin.

This library was written exclusively by me for the needs of projects. In each project, for security reasons, we do not want to collect information about errors using libraries from third parties. So, I wrote a set of classes that capture information about uncaught crashes and send them to the address indicated.

Second option in this library is to save them to file. In the event of server problems, users can find this file on their phone and send it to us for verification purposes.

## Timber logger support

Also, this library supports [Timber](https://github.com/JakeWharton/timber) logger by Jake Wharton. Developer can plant modified TimberTree in Jake's library.

```kotlin
//add Timber implementation in gradle and plant Timber tree with MagicLogs
Timber.plant(MagicLogsTree(applicationContext))
```

And use Timber library to send information about each handled non-critic exception in app.

```kotlin
//Each log logged by `Timber.e` will be send to server or saved in file
Timber.e(RuntimeException("FatalException"))
```

## Usage

Initialize `MagicLogs` library before you use it. It can be done in your `Application` class.

```kotlin
class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        
        //add AndroidThreeTen implementation in gradle and init it
        AndroidThreeTen.init(this)
        //create builder of MagicLogs and set properties
        val magiclogs = MagicLogs.Builder()
                .context(applicationContext)
                .user("sample.user@external.engie.com")
                .apiUrl(BuildConfig.API_URL)
                .appVersionName(BuildConfig.APP_VERSION)
                .logsTokenProvider(logsAccessTokenProvider)
                .options(MagicLogsOptions.OPTION_SERVER_LOGS or MagicLogsOptions.OPTION_FILE_LOGS)
                .build()

        //init MagicLogs
        magiclogs.init()
        
        //(optional) plant Timber tree with MagicLogs
        Timber.plant(MagicLogsTree(applicationContext))
    }
}
```

Starting from now, all crashes of application will be caught by `MagicLogs` sent to server and saved to file.

If you planted TimberTree, information will be sent/saved every time you use `Timber.e()` 

```kotlin
Timber.e(RuntimeException("FatalException"))
```



## How it works

1. MagicLogs use own modified `Thread.UncaughtExceptionHandler` to catch unhandled exceptions and `MagicLogsTree` to capture logs from `Timber` 
2. Exception with some addiotional informations are stored in local `Room Persistance Library` . And saved to file, if enabled
3.  `SendLogJob` is scheduled. And logs are sent when network is available.

## Used libraries

```groovy
implementation "org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.2.51"

implementation "com.android.support:appcompat-v7:27.1.1"

implementation "com.jakewharton.timber:timber:4.7.0"

implementation "io.reactivex.rxjava2:rxjava:2.1.9"
implementation "io.reactivex.rxjava2:rxandroid:2.0.2"

implementation "android.arch.lifecycle:extensions:1.1.1"

implementation "com.squareup.retrofit2:retrofit:2.4.0"
implementation "com.squareup.retrofit2:adapter-rxjava2:2.4.0"
implementation 'com.squareup.retrofit2:converter-gson:2.4.0'

implementation "com.squareup.okhttp3:okhttp:3.10.0"

implementation "android.arch.persistence.room:runtime:1.1.0"
implementation "android.arch.persistence.room:rxjava2:1.1.0"

implementation "com.evernote:android-job:1.2.5"

implementation 'com.google.code.gson:gson:2.8.4'

implementation "com.jakewharton.threetenabp:threetenabp:1.0.5"

implementation "com.google.android.gms:play-services-gcm:15.0.0"

kapt "android.arch.persistence.room:compiler:1.1.0"

testImplementation "org.threeten:threetenbp:1.3.6"
debugImplementation "com.facebook.stetho:stetho:1.5.0"
debugImplementation "com.facebook.stetho:stetho-okhttp3:1.5.0"
debugImplementation 'com.squareup.okhttp3:logging-interceptor:3.10.0'
```

Popular library like `Dagger2` is very useful in many cases. But it is not used in this library, because I want to reduce weight of this library as much as possible.