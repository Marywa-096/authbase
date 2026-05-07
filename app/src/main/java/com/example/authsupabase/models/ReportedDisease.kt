package com.example.authsupabase.models

import kotlinx.serialization.Serializable

@Serializable
data class ReportedDisease(
    val id: Int? = null,
    val userId: String,
    val diseaseName: String,
    val symptoms: String,
    val date: String
)