package com.example.authsupabase.ui.screens.management

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.authsupabase.ui.screens.records.viewmodel.HealthViewModel

@Composable
fun ManagementScreen(
    navController: NavHostController,
    healthViewModel: HealthViewModel = viewModel()
) {
    var bloodPressure by remember { mutableStateOf("") }
    var sugarLevel by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            @OptIn(ExperimentalMaterial3Api::class)
            TopAppBar(title = { Text("Disease Management") })
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).padding(16.dp)) {
            Text("Track Your Health", style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(16.dp))
            
            OutlinedTextField(
                value = bloodPressure,
                onValueChange = { bloodPressure = it },
                label = { Text("Blood Pressure (mmHg)") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            
            OutlinedTextField(
                value = sugarLevel,
                onValueChange = { sugarLevel = it },
                label = { Text("Sugar Level (mg/dL)") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            
            Button(
                onClick = { 
                    if (bloodPressure.isNotEmpty()) {
                        healthViewModel.addRecord("Blood Pressure", bloodPressure, "mmHg")
                    }
                    if (sugarLevel.isNotEmpty()) {
                        healthViewModel.addRecord("Sugar Level", sugarLevel, "mg/dL")
                    }
                    navController.popBackStack()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save Records")
            }
        }
    }
}
