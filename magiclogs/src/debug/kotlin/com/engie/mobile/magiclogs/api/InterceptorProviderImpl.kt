package com.engie.mobile.magiclogs.api

import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor

object InterceptorProviderImpl : InterceptorProvider {
    override fun provideInterceptors(): List<Interceptor> {
        return listOf(provideLoggingInterceptor())
    }

    override fun provideNetworkInterceptors(): List<Interceptor> {
        return listOf(stethoInterceptor())
    }

    private fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }

    private fun stethoInterceptor() = StethoInterceptor()
}