package com.pregnancytracker.vitals.notification

import android.content.Context
import androidx.work.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationManager @Inject constructor(
    private val context: Context
) {
    fun scheduleVitalsReminder() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
            .build()

        val reminderRequest = PeriodicWorkRequestBuilder<VitalsReminderWorker>(
            5, TimeUnit.HOURS
        )
            .setConstraints(constraints)
            .setInitialDelay(5, TimeUnit.HOURS)
            .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            VitalsReminderWorker.WORK_NAME,
            ExistingPeriodicWorkPolicy.REPLACE,
            reminderRequest
        )
    }

    fun cancelVitalsReminder() {
        WorkManager.getInstance(context).cancelUniqueWork(VitalsReminderWorker.WORK_NAME)
    }
}
