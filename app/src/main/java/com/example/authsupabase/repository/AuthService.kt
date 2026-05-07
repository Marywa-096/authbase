package com.example.authsupabase.repository

import com.example.authsupabase.models.UserModel


interface AuthService {
    suspend fun registerUser(user: UserModel)
    suspend fun loginUser(user: UserModel)
    suspend fun resetPassword(email: String)
    suspend fun getUserProfile(user: UserModel)
    suspend fun logoutUser()



}