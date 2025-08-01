package com.pregnancytracker.vitals.data.dao

import androidx.room.*
import com.pregnancytracker.vitals.data.entity.VitalsEntry
import kotlinx.coroutines.flow.Flow

@Dao
interface VitalsDao {
    @Query("SELECT * FROM vitals_entries ORDER BY timestamp DESC")
    fun getAllVitals(): Flow<List<VitalsEntry>>

    @Query("SELECT * FROM vitals_entries WHERE id = :id")
    suspend fun getVitalsById(id: Long): VitalsEntry?

    @Insert
    suspend fun insertVitals(vitals: VitalsEntry): Long

    @Update
    suspend fun updateVitals(vitals: VitalsEntry)

    @Delete
    suspend fun deleteVitals(vitals: VitalsEntry)

    @Query("DELETE FROM vitals_entries WHERE id = :id")
    suspend fun deleteVitalsById(id: Long)

    @Query("SELECT * FROM vitals_entries WHERE timestamp BETWEEN :startDate AND :endDate ORDER BY timestamp DESC")
    fun getVitalsByDateRange(startDate: Long, endDate: Long): Flow<List<VitalsEntry>>
}
