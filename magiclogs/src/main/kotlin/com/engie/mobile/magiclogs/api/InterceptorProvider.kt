package com.engie.mobile.magiclogs.api

import okhttp3.Interceptor

interface InterceptorProvider {
    fun provideInterceptors(): List<Interceptor>

    fun provideNetworkInterceptors(): List<Interceptor>
}