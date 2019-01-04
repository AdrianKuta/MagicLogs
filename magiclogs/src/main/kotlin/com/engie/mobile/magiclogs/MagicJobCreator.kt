package com.engie.mobile.magiclogs

import com.engie.mobile.magiclogs.data.SendLogJob
import com.evernote.android.job.Job
import com.evernote.android.job.JobCreator

class MagicJobCreator : JobCreator {
    override fun create(tag: String): Job? {
        return when (tag) {
            SendLogJob.JOB_TAG -> SendLogJob()
            else -> null
        }
    }
}