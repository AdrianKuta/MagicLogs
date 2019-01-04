package com.engie.mobile.magiclogs.api

import com.engie.mobile.magiclogs.MagicLogsOptions
import com.engie.mobile.magiclogs.utils.UnitConverterFactory
import com.google.gson.GsonBuilder
import io.reactivex.schedulers.Schedulers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.threeten.bp.LocalDateTime
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object LogClient {

    private fun provideOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()

        val interceptors: List<Interceptor> = InterceptorProviderImpl.provideInterceptors()
        val networkInterceptors = InterceptorProviderImpl.provideNetworkInterceptors()

        interceptors.forEach { builder.addInterceptor(it) }
        networkInterceptors.forEach { builder.addNetworkInterceptor(it) }
        return builder.build()
    }

    private fun provideGsonConverterFactory(): GsonConverterFactory {
        val gson = GsonBuilder()
                .registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeAdapter())
                .create()
        return GsonConverterFactory.create(gson)
    }

    val logClient = Retrofit.Builder()
            .baseUrl(MagicLogsOptions.apiUrl)
            .client(provideOkHttpClient())
            .addConverterFactory(UnitConverterFactory)
            .addConverterFactory(provideGsonConverterFactory())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .build()
            .create(LogService::class.java)
}