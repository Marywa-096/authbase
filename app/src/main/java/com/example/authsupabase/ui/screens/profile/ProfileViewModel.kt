package com.example.authsupabase.ui.screens.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.authsupabase.models.UserProfile
import com.example.authsupabase.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {
    private val authRepository = AuthRepository()

    private val _profile = MutableStateFlow<UserProfile?>(null)
    val profile = _profile.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _message = MutableStateFlow<String?>(null)
    val message = _message.asStateFlow()

    init {
        fetchProfile()
    }

    fun fetchProfile() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _profile.value = authRepository.getCurrentUserProfile()
            } catch (e: Exception) {
                _message.value = "Failed to load profile: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun updateProfile(fullName: String, phoneNumber: String) {
        val currentProfile = _profile.value ?: return
        val updatedProfile = currentProfile.copy(
            full_name = fullName,
            phone_number = phoneNumber
        )
        
        viewModelScope.launch {
            _isLoading.value = true
            try {
                authRepository.updateUserProfile(updatedProfile)
                _profile.value = updatedProfile
                _message.value = "Profile updated successfully"
            } catch (e: Exception) {
                _message.value = "Failed to update profile: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun clearMessage() {
        _message.value = null
    }

    fun logout(onSuccess: () -> Unit) {
        viewModelScope.launch {
            authRepository.logoutUser()
            onSuccess()
        }
    }
}
