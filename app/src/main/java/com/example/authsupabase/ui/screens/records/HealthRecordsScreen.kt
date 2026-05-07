package com.example.authsupabase.ui.screens.records

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

import androidx.compose.ui.Alignment
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.authsupabase.ui.screens.records.viewmodel.HealthViewModel

@Composable
fun HealthRecordsScreen(
    navController: NavHostController,
    healthViewModel: HealthViewModel = viewModel()
) {
    val records by healthViewModel.records.collectAsState()
    val isLoading by healthViewModel.isLoading.collectAsState()

    LaunchedEffect(Unit) {
        healthViewModel.fetchRecords()
    }

    Scaffold(
        topBar = {
            @OptIn(ExperimentalMaterial3Api::class)
            TopAppBar(title = { Text("Health Records") })
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).padding(16.dp)) {
            Text("Your History", style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(16.dp))
            
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            } else {
                LazyColumn {
                    items(records) { record ->
                        ListItem(
                            headlineContent = { Text("${record.type}: ${record.value} ${record.unit}") },
                            supportingContent = { Text("Date: ${record.date}") }
                        )
                    }
                }
            }
        }
    }
}
