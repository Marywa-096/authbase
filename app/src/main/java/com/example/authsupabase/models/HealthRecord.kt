package com.example.authsupabase.models

import kotlinx.serialization.Serializable

@Serializable
data class HealthRecord(
    val id: Int? = null,
    val userId: String,
    val type: String, // e.g., "Blood Pressure", "Sugar Level"
    val value: String,
    val unit: String,
    val date: String,
    val notes: String? = null
)