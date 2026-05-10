package com.example.authsupabase.ui.screens.reminders

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.authsupabase.ui.navigation.ROUTES

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RemindersScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Reminders") },
                actions = {
                    IconButton(onClick = { navController.navigate(ROUTES.Notifications.name) }) {
                        BadgedBox(
                            badge = {
                                Badge { Text("3") }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Notifications,
                                contentDescription = "Notifications"
                            )
                        }
                    }
                }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).padding(16.dp)) {
            Text("Medication & Appointments", style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(16.dp))
            
            Button(
                onClick = { /* TODO: Add Reminder */ },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Add New Reminder")
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun RemindersScreenPreview() {
    RemindersScreen(navController = rememberNavController())
}

