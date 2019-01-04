package com.engie.mobile.magiclogs.extensions

import retrofit2.Response
import timber.log.Timber

fun Response<Unit>.toBodyOrError() {
    if (!isSuccessful) throw HttpRequestException(code())
}

fun <T> Response<T>.toBodyOrError(): T {
    return if (isSuccessful) {
        body()!!
    } else {
        Timber.e(errorBody()?.string())
        throw HttpRequestException(code())
    }
}

class HttpRequestException(val errorCode: Int) : RuntimeException()