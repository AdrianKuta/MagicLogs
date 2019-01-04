package com.engie.mobile.magiclogs.data

import com.engie.mobile.magiclogs.api.LogDto
import com.engie.mobile.magiclogs.domain.Log

class LogMapper : (Log) -> LogDto {
    override fun invoke(log: Log): LogDto {
        return LogDto(
                log.appVersionName,
                log.androidVersion,
                log.phoneModel,
                log.message,
                log.stack,
                log.user,
                log.date
        )
    }
}