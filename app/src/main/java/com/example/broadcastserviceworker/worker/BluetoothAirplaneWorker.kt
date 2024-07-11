package com.example.broadcastserviceworker.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import androidx.work.worker
import kotlinx.coroutines.delay

class BluetoothAirplaneWorker(context: Context, params: WorkerParameters) : CoroutineWorker(context, params) {

    companion object {
        const val WORK_TAG = "worker_airplane"
        const val INTERVAL_MILLIS = 2 * 60 * 1000L // 2 minutes in milliseconds
    }

    override suspend fun doWork(): Result {
        try {
            while (true) {
                val bluetoothStatus = Settings.Global.getInt(applicationContext.contentResolver, Settings.Global.BLUETOOTH_ON, 0)
                val airplaneMode = Settings.Global.getInt(applicationContext.contentResolver, Settings.Global.AIRPLANE_MODE_ON, 0)

                Log.i(WORK_TAG, "Bluetooth Status: $bluetoothStatus, Airplane Mode: $airplaneMode")

                delay(INTERVAL_MILLIS)
            }
        } catch (e: Exception) {
            Log.e(WORK_TAG, "Error in periodic work: ${e.message}", e)
            return Result.failure()
        }
        return Result.success()
    }
}

fun scheduleBluetoothAirplaneWorker(context: Context) {
    val constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .build()

    val periodicWorkRequest = PeriodicWorkRequestBuilder<BluetoothAirplaneWorker>(
        repeatInterval = BluetoothAirplaneWorker.INTERVAL_MILLIS,
        flexTimeInterval = BluetoothAirplaneWorker.INTERVAL_MILLIS / 2,
        timeUnit = TimeUnit.MILLISECONDS
    )
        .setConstraints(constraints)
        .addTag(BluetoothAirplaneWorker.WORK_TAG)
        .build()

    WorkManager.getInstance(context).enqueueUniquePeriodicWork(
        "BluetoothAirplaneWorker",
        ExistingPeriodicWorkPolicy.REPLACE,
        periodicWorkRequest
    )
}