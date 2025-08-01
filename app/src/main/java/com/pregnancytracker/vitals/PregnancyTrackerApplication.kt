package com.pregnancytracker.vitals

import android.app.Application
import com.pregnancytracker.vitals.data.database.VitalsDatabase
import com.pregnancytracker.vitals.data.repository.VitalsRepository
import com.pregnancytracker.vitals.notification.NotificationManager

class PregnancyTrackerApplication : Application() {
    
    val database by lazy { VitalsDatabase.getDatabase(this) }
    val repository by lazy { VitalsRepository(database.vitalsDao()) }
    val notificationManager by lazy { NotificationManager(this) }
    
    override fun onCreate() {
        super.onCreate()
        // Schedule initial notification
        notificationManager.scheduleVitalsReminder()
    }
}
