package com.example.authsupabase.repository

import com.example.authsupabase.models.HealthRecord
import com.example.authsupabase.network.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.postgrest

class HealthRepository {
    private val supabase = SupabaseClient.client

    suspend fun saveHealthRecord(record: HealthRecord) {
        supabase.postgrest["health_records"].insert(record)
    }

    suspend fun getHealthRecords(userId: String): List<HealthRecord> {
        return supabase.postgrest["health_records"]
            .select {
                filter {
                    eq("userId", userId)
                }
            }
            .decodeList<HealthRecord>()
    }
    
    fun getCurrentUserId(): String? {
        return supabase.auth.currentUserOrNull()?.id
    }
}
