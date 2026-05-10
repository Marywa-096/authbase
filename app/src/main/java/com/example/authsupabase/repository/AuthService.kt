package com.example.authsupabase.repository

import com.example.authsupabase.models.UserModel
import com.example.authsupabase.models.UserProfile


interface AuthService {
    suspend fun registerUser(user: UserModel)
    suspend fun loginUser(user: UserModel)
    suspend fun resetPassword(email: String)
    suspend fun getUserProfile(user: UserModel)
    suspend fun getCurrentUserProfile(): UserProfile?
    suspend fun updateUserProfile(profile: UserProfile)
    suspend fun logoutUser()
}
