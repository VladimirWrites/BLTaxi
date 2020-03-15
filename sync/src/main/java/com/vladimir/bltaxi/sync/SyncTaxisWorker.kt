package com.vladimir.bltaxi.sync

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import java.lang.Exception

internal class SyncTaxisWorker(
    context: Context,
    workerParams: WorkerParameters,
    private val syncTaxis: SyncTaxis
): CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result = coroutineScope {
        try {
            syncTaxis()
        } catch (e: Exception) {
            Result.failure()
        }
        syncTaxis()
        Result.success()
    }
}
