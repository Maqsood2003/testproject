package com.pregnancytracker.vitals.data.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import android.content.Context
import com.pregnancytracker.vitals.data.converter.DateConverter
import com.pregnancytracker.vitals.data.dao.VitalsDao
import com.pregnancytracker.vitals.data.entity.VitalsEntry

@Database(
    entities = [VitalsEntry::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(DateConverter::class)
abstract class VitalsDatabase : RoomDatabase() {
    abstract fun vitalsDao(): VitalsDao

    companion object {
        @Volatile
        private var INSTANCE: VitalsDatabase? = null

        fun getDatabase(context: Context): VitalsDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    VitalsDatabase::class.java,
                    "vitals_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
