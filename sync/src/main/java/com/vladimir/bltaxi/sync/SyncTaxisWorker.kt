package com.vladimir.bltaxi.sync

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.coroutineScope
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.lang.Exception

internal class SyncTaxisWorker(
    context: Context,
    workerParams: WorkerParameters
): CoroutineWorker(context, workerParams), KoinComponent {
    override suspend fun doWork(): Result = coroutineScope {
        val syncTaxis: SyncTaxis by inject()
        try {
            syncTaxis()
        } catch (e: Exception) {
            Result.failure()
        }
        Result.success()
    }
}
