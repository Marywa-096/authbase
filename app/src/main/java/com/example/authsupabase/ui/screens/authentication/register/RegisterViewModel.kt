package com.example.authsupabase.ui.screens.authentication.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.authsupabase.models.UserModel
import com.example.authsupabase.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {

    private val authRepository = AuthRepository()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _message = MutableStateFlow("")
    val message = _message.asStateFlow()

    fun registerUser(userModel: UserModel) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                authRepository.registerUser(userModel)
                _isLoading.value = false
                _message.value = "Account created successfully! Please login."
            } catch (e: Exception) {
                e.printStackTrace()
                _isLoading.value = false
                _message.value = "Registration failed: ${e.message}"
            }
        }
    }
}
