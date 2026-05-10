package com.example.authsupabase.models

import kotlinx.serialization.Serializable

@Serializable
data class UserModel(
    val fullname: String = "",
    val email: String = "",
    val password: String = ""
)
