package com.engie.mobile.magiclogs.data

sealed class SendLogResult {
    object NoLogsToSend : SendLogResult()
    object Success : SendLogResult()
    object Error : SendLogResult()

}