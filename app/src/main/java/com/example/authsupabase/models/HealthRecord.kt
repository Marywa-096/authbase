package com.example.authsupabase.models

import kotlinx.serialization.Serializable

@Serializable
data class HealthRecord(
    val id: Int? = null,
    val userId: String? = null,
    val type: String? = null,
    val value: String? = null,
    val unit: String? = null,
    val blood_pressure: String? = null,
    val sugar_level: String? = null,
    val weight: String? = null,
    val notes: String? = null,
    val date: String? = null
)