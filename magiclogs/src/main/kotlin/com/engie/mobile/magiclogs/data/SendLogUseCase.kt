package com.engie.mobile.magiclogs.data

import com.engie.mobile.magiclogs.MagicLogsOptions
import com.engie.mobile.magiclogs.api.LogClient
import com.engie.mobile.magiclogs.domain.Log
import com.engie.mobile.magiclogs.domain.LogStatus
import com.engie.mobile.magiclogs.extensions.toBodyOrError
import io.reactivex.MaybeSource
import io.reactivex.Single
import io.reactivex.SingleSource
import io.reactivex.SingleTransformer
import timber.log.Timber

class SendLogUseCase : SingleTransformer<SendLogAction, SendLogResult> {

    private val logDao = MagicLogsOptions.database.logDao()
    private val logService = LogClient.logClient
    private val logMapper = LogMapper()

    override fun apply(action: Single<SendLogAction>): SingleSource<SendLogResult> {
        return action.flatMapMaybe {logDao.findOldestNotSentLog()}
                .concatMap { postLog(it) }
                .toSingle(SendLogResult.NoLogsToSend as SendLogResult)
    }

    private fun postLog(log: Log): MaybeSource<SendLogResult> {
        return logService.postLog(logMapper(log), "Bearer ${MagicLogsOptions.accessToken}")
                .map { it.toBodyOrError() }
                .doOnSuccess { changeStatus(log) }
                .map { SendLogResult.Success as SendLogResult }
                .onErrorReturn(this::handleError)
                .toMaybe()
    }

    private fun changeStatus(log: Log) {
        logDao.updateLogStatus(log.id, LogStatus.SENT)
    }

    private fun handleError(throwable: Throwable): SendLogResult {
        Timber.e(throwable)
        return SendLogResult.Error
    }
}