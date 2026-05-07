package com.example.authsupabase.ui.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.authsupabase.ui.navigation.ROUTES

import com.example.authsupabase.network.SupabaseClient
import io.github.jan.supabase.auth.auth

@Composable
fun HomeScreen(navController: NavHostController) {
    val userEmail = SupabaseClient.client.auth.currentUserOrNull()?.email ?: "User"
    val userName = userEmail.substringBefore("@")
    
    val menuItems = listOf(
        "Prevention" to ROUTES.Prevention,
        "Management" to ROUTES.Management,
        "Records" to ROUTES.HealthRecords,
        "Reminders" to ROUTES.Reminders,
        "Emergency" to ROUTES.Emergency
    )

    Scaffold(
        topBar = {
            @OptIn(ExperimentalMaterial3Api::class)
            TopAppBar(title = { Text("Health Dashboard") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            Text(
                text = "Hello, $userName!",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "Welcome to KNOW YOUR HEALTH. Stay proactive about your well-being today.",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            Box(modifier = Modifier.weight(1f)) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(menuItems) { (title, route) ->
                        Card(
                            onClick = { navController.navigate(route.name) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(120.dp),
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                        ) {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier.fillMaxSize()
                            ) {
                                Text(
                                    text = title,
                                    style = MaterialTheme.typography.titleLarge
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { navController.navigate(ROUTES.ReportDisease.name) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer,
                    contentColor = MaterialTheme.colorScheme.onErrorContainer
                )
            ) {
                Text(
                    text = "Report Disease",
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}
