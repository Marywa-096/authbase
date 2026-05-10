package com.example.authsupabase.ui.screens.guidance

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.authsupabase.ui.navigation.ROUTES

@Composable
fun GuidanceScreen(navController: NavHostController) {
    val guidanceList = listOf(
        "Maintain a balanced diet rich in fiber and low in processed sugars.",
        "Engage in at least 30 minutes of moderate physical activity daily.",
        "Monitor your vital signs (Blood Pressure, Sugar levels) regularly.",
        "Stay hydrated and ensure you get adequate rest.",
        "Follow your prescribed medication schedule strictly.",
        "Consult with a healthcare professional for personalized advice."
    )

    Scaffold(
        topBar = {
            @OptIn(ExperimentalMaterial3Api::class)
            TopAppBar(title = { Text("Control & Prevention") })
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).padding(16.dp)) {
            Text(
                text = "Condition Management Guidance",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(guidanceList) { advice ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Text(
                            text = advice,
                            modifier = Modifier.padding(16.dp),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { 
                    navController.navigate(ROUTES.Home.name) {
                        popUpTo(ROUTES.ReportDisease.name) { inclusive = true }
                    } 
                },
                modifier = Modifier.fillMaxWidth().height(56.dp)
            ) {
                Text("Go to Dashboard")
            }
        }
    }
}
