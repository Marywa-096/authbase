package com.example.authsupabase.models

import kotlinx.serialization.Serializable

@Serializable
data class UserModel(
    val email:String = "",
    val password:String = ""

)