package com.example.authsupabase.ui.screens.authentication.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.authsupabase.models.UserModel
import com.example.authsupabase.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val authRepository = AuthRepository()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _message = MutableStateFlow("")
    val message = _message.asStateFlow()

    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn = _isLoggedIn.asStateFlow()

    fun loginUser(userModel: UserModel) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                authRepository.loginUser(userModel)
                _isLoading.value = false
                _isLoggedIn.value = true
                _message.value = "Login Successful!"
            } catch (e: Exception) {
                _isLoading.value = false
                _message.value = "Login Failed: ${e.message}"
            }
        }
    }
}
