package com.example.authsupabase.ui.screens.records.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.authsupabase.models.HealthRecord
import com.example.authsupabase.repository.HealthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class HealthViewModel : ViewModel() {
    private val healthRepository = HealthRepository()

    private val _records = MutableStateFlow<List<HealthRecord>>(emptyList())
    val records = _records.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    fun fetchRecords() {
        val userId = healthRepository.getCurrentUserId() ?: return
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _records.value = healthRepository.getHealthRecords(userId)
            } catch (e: Exception) {
                // Handle error
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun addRecord(type: String, value: String, unit: String) {
        val userId = healthRepository.getCurrentUserId() ?: return
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val current = sdf.format(Date())
        
        val record = HealthRecord(
            userId = userId,
            type = type,
            value = value,
            unit = unit,
            date = current
        )

        viewModelScope.launch {
            try {
                healthRepository.saveHealthRecord(record)
                fetchRecords() // Refresh list
            } catch (e: Exception) {
                // Handle error
            }
        }
    }
}
