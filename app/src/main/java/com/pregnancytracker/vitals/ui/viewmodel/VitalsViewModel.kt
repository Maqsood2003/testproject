package com.pregnancytracker.vitals.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pregnancytracker.vitals.data.entity.VitalsEntry
import com.pregnancytracker.vitals.data.repository.VitalsRepository
import com.pregnancytracker.vitals.notification.NotificationManager
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class VitalsUiState(
    val vitalsEntries: List<VitalsEntry> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val showAddDialog: Boolean = false
)

class VitalsViewModel @Inject constructor(
    private val repository: VitalsRepository,
    private val notificationManager: NotificationManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(VitalsUiState())
    val uiState: StateFlow<VitalsUiState> = _uiState.asStateFlow()

    init {
        loadVitals()
        scheduleNotifications()
    }

    private fun loadVitals() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                repository.getAllVitals().collect { vitals ->
                    _uiState.update { 
                        it.copy(
                            vitalsEntries = vitals,
                            isLoading = false,
                            errorMessage = null
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.update { 
                    it.copy(
                        isLoading = false,
                        errorMessage = e.message
                    )
                }
            }
        }
    }

    fun addVitals(
        systolicBP: Int,
        diastolicBP: Int,
        heartRate: Int,
        weight: Double,
        babyKicksCount: Int,
        notes: String = ""
    ) {
        viewModelScope.launch {
            try {
                val vitalsEntry = VitalsEntry(
                    systolicBP = systolicBP,
                    diastolicBP = diastolicBP,
                    heartRate = heartRate,
                    weight = weight,
                    babyKicksCount = babyKicksCount,
                    notes = notes
                )
                repository.insertVitals(vitalsEntry)
                hideAddDialog()
            } catch (e: Exception) {
                _uiState.update { 
                    it.copy(errorMessage = e.message)
                }
            }
        }
    }

    fun deleteVitals(vitals: VitalsEntry) {
        viewModelScope.launch {
            try {
                repository.deleteVitals(vitals)
            } catch (e: Exception) {
                _uiState.update { 
                    it.copy(errorMessage = e.message)
                }
            }
        }
    }

    fun showAddDialog() {
        _uiState.update { it.copy(showAddDialog = true) }
    }

    fun hideAddDialog() {
        _uiState.update { it.copy(showAddDialog = false) }
    }

    fun clearErrorMessage() {
        _uiState.update { it.copy(errorMessage = null) }
    }

    private fun scheduleNotifications() {
        notificationManager.scheduleVitalsReminder()
    }
}
