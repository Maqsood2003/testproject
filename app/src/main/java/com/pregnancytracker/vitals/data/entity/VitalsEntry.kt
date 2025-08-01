package com.pregnancytracker.vitals.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "vitals_entries")
data class VitalsEntry(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val systolicBP: Int,
    val diastolicBP: Int,
    val heartRate: Int,
    val weight: Double,
    val babyKicksCount: Int,
    val timestamp: Date = Date(),
    val notes: String = ""
)
