package com.engie.mobile.magiclogs.data

import com.evernote.android.job.Job
import com.evernote.android.job.JobRequest
import io.reactivex.Observable
import io.reactivex.Single
import java.util.concurrent.TimeUnit

class SendLogJob : Job() {

    private val sendLogUseCase = SendLogUseCase()

    override fun onRunJob(params: Params): Result {

        val sendLogResult = Single.just(SendLogAction)
                .compose(sendLogUseCase)
                .blockingGet()

        return when (sendLogResult) {
            SendLogResult.NoLogsToSend -> Result.SUCCESS
            SendLogResult.Success -> Result.RESCHEDULE
            else -> Result.FAILURE
        }
    }

    companion object {
        const val JOB_TAG = "SendLogJob"

        fun buildJobRequest(): JobRequest {
            return JobRequest.Builder(JOB_TAG)
                    .setRequiredNetworkType(JobRequest.NetworkType.CONNECTED)
                    .setExecutionWindow(TimeUnit.SECONDS.toMillis(1), TimeUnit.MINUTES.toMillis(5))
                    .setRequirementsEnforced(true)
                    .build()
        }
    }
}