package com.engie.mobile.magiclogs

import android.content.Context
import com.facebook.stetho.Stetho

object DevelopmentSettings {

    fun init(context: Context) {
        Stetho.initializeWithDefaults(context)
    }
}