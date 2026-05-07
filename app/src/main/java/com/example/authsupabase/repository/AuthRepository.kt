package com.example.authsupabase.repository

import com.example.authsupabase.models.UserModel
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest

class AuthRepository : AuthService {
    private val supabase = createSupabaseClient(
        supabaseUrl = "https://xtzvalmhpywlnhzhsjhg.supabase.co",
        supabaseKey = "sb_publishable_0xmF5oJHztzL0pVctB81nw_YQ-48ItT"
    ) {
        install(Postgrest)
        install(Auth)
    }

    override suspend fun registerUser(user: UserModel) {
        supabase.auth.signUpWith(Email) {
            email = user.email
            password = user.password
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

    override suspend fun logoutUser() {
        supabase.auth.signOut()
    }
}
