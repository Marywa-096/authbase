package com.example.authsupabase.repository

import com.example.authsupabase.models.UserModel
import com.example.authsupabase.models.UserProfile
import com.example.authsupabase.network.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

class AuthRepository : AuthService {
    private val supabase = SupabaseClient.client

    override suspend fun registerUser(user: UserModel) {
        supabase.auth.signUpWith(Email) {
            email = user.email
            password = user.password
            data = buildJsonObject {
                put("full_name", user.fullname)
            }
        }
    }

    override suspend fun loginUser(user: UserModel) {
        supabase.auth.signInWith(Email) {
            email = user.email
            password = user.password
        }
    }

    override suspend fun resetPassword(email: String) {
        supabase.auth.resetPasswordForEmail(email = email)
    }

    override suspend fun getUserProfile(user: UserModel) {
        // TODO("Not yet implemented")
    }

    override suspend fun getCurrentUserProfile(): UserProfile? {
        val user = supabase.auth.currentUserOrNull() ?: return null
        return supabase.postgrest["profiles"]
            .select {
                filter {
                    eq("id", user.id)
                }
            }
            .decodeSingleOrNull<UserProfile>()
    }

    override suspend fun updateUserProfile(profile: UserProfile) {
        val user = supabase.auth.currentUserOrNull() ?: return
        supabase.postgrest["profiles"].update(profile) {
            filter {
                eq("id", user.id)
            }
        }
    }

    override suspend fun logoutUser() {
        supabase.auth.signOut()
    }
}
