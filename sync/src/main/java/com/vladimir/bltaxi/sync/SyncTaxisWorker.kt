package com.vladimir.bltaxi.sync

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.coroutineScope
import java.lang.Exception

@HiltWorker
class SyncTaxisWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val syncTaxis: SyncTaxis
): CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result = coroutineScope {
        try {
            syncTaxis()
        } catch (e: Exception) {
            Result.failure()
        }
        Result.success()
    }
}
