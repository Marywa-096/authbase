package com.example.authsupabase.ui.screens.records

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.authsupabase.ui.screens.records.viewmodel.HealthViewModel

@OptIn(ExperimentalMaterial3Api::class)
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
            TopAppBar(title = { Text("Health Records") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                // This uses the code logic you requested
                healthViewModel.addRecord(
                    bloodPressure = "120/80",
                    sugarLevel = "5.6",
                    weight = "60kg",
                    notes = "Feeling okay"
                )
            }) {
                Icon(Icons.Default.Add, contentDescription = "Add Record")
            }
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).padding(16.dp)) {
            Text("Your History", style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(16.dp))
            
            if (isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(records) { record ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                        ) {
                            ListItem(
                                headlineContent = { 
                                    Text(
                                        text = if (record.blood_pressure != null) 
                                            "BP: ${record.blood_pressure} | Sugar: ${record.sugar_level}"
                                        else 
                                            "${record.type ?: "Record"}: ${record.value ?: ""} ${record.unit ?: ""}"
                                    )
                                },
                                supportingContent = {
                                    Column {
                                        record.weight?.let { Text("Weight: $it") }
                                        Text("Date: ${record.date ?: "N/A"}")
                                        record.notes?.let { Text("Notes: $it") }
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
