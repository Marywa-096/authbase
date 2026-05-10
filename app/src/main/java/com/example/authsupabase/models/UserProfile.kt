package com.example.authsupabase.models

import kotlinx.serialization.Serializable

@Serializable
data class UserProfile(
    val id: String? = null,
    val full_name: String? = null,
    val email: String? = null,
    val phone_number: String? = null,
    val avatar_url: String? = null,
    val updated_at: String? = null
)
