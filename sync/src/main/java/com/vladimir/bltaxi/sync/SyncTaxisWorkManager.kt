package com.vladimir.bltaxi.sync

import android.os.Build
import androidx.work.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

private const val SYNC_TAXIS_WORK_NAME = "sync_taxis"

class SyncTaxisWorkManager(
    private val workManager: WorkManager
) {
    private val constraints = Constraints.Builder().apply {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            setRequiresDeviceIdle(true)
        }
        setRequiresCharging(true)
        setRequiredNetworkType(NetworkType.CONNECTED)
    }.build()

    private val syncTaxisRequest = PeriodicWorkRequestBuilder<SyncTaxisWorker>(1, TimeUnit.DAYS)
        .setInitialDelay(1, TimeUnit.DAYS)
        .setConstraints(constraints)
        .build()

    fun start() {
        workManager.enqueueUniquePeriodicWork(SYNC_TAXIS_WORK_NAME, ExistingPeriodicWorkPolicy.KEEP, syncTaxisRequest)
    }
}


