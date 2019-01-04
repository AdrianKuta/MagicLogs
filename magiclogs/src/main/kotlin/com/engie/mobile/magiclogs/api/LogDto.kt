package com.engie.mobile.magiclogs.api

import org.threeten.bp.LocalDateTime

data class LogDto(
        val AppVersionName: String,
        val AndroidVersion: String,
        val PhoneModel: String,
        val Message: String,
        val Stack: String,
        val User: String?,
        val Date: LocalDateTime
)