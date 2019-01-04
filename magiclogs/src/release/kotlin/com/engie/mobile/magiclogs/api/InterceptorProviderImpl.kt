package com.engie.mobile.magiclogs.api

import okhttp3.Interceptor

object InterceptorProviderImpl : InterceptorProvider {
    override fun provideInterceptors(): List<Interceptor> {
        return emptyList()
    }

    override fun provideNetworkInterceptors(): List<Interceptor> {
        return emptyList()
    }
}