package com.pregnancytracker.vitals.data.repository

import com.pregnancytracker.vitals.data.dao.VitalsDao
import com.pregnancytracker.vitals.data.entity.VitalsEntry
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VitalsRepository @Inject constructor(
    private val vitalsDao: VitalsDao
) {
    fun getAllVitals(): Flow<List<VitalsEntry>> = vitalsDao.getAllVitals()

    suspend fun getVitalsById(id: Long): VitalsEntry? = vitalsDao.getVitalsById(id)

    suspend fun insertVitals(vitals: VitalsEntry): Long = vitalsDao.insertVitals(vitals)

    suspend fun updateVitals(vitals: VitalsEntry) = vitalsDao.updateVitals(vitals)

    suspend fun deleteVitals(vitals: VitalsEntry) = vitalsDao.deleteVitals(vitals)

    suspend fun deleteVitalsById(id: Long) = vitalsDao.deleteVitalsById(id)

    fun getVitalsByDateRange(startDate: Long, endDate: Long): Flow<List<VitalsEntry>> =
        vitalsDao.getVitalsByDateRange(startDate, endDate)
}
