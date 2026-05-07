package com.example.authsupabase.network

import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest

object SupabaseClient {
    const val URL = "https://xtzvalmhpywlnhzhsjhg.supabase.co"
    // IMPORTANT: Ensure this is your 'anon' key from Supabase settings
    const val KEY = "sb_publishable_0xmF5oJHztzL0pVctB81nw_YQ-48ItT"

    val client = createSupabaseClient(
        supabaseUrl = URL,
        supabaseKey = KEY
    ) {
        install(Postgrest)
        install(Auth)
    }
}
