package com.engie.mobile.magiclogs.api

import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface LogService {

    @POST("Sync/MobileDiagnostic")
    fun postLog(@Body log: LogDto, @Header("Authorization") token: String): Single<Response<Unit>>
}