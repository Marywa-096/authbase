package com.example.authsupabase.ui.screens.report

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.authsupabase.ui.navigation.ROUTES
import com.example.authsupabase.ui.screens.records.viewmodel.HealthViewModel

@Composable
fun ReportDiseaseScreen(
    navController: NavHostController,
    healthViewModel: HealthViewModel = viewModel()
) {
    var diseaseName by remember { mutableStateOf("") }
    var symptoms by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            @OptIn(ExperimentalMaterial3Api::class)
            TopAppBar(title = { Text("Report Disease") })
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).padding(16.dp)) {
            Text("Report a Health Issue", style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(16.dp))
            
            OutlinedTextField(
                value = diseaseName,
                onValueChange = { diseaseName = it },
                label = { Text("Disease/Condition Name") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            
            OutlinedTextField(
                value = symptoms,
                onValueChange = { symptoms = it },
                label = { Text("Describe Symptoms") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )
            Spacer(modifier = Modifier.height(16.dp))
            
            Button(
                onClick = { 
                    if (diseaseName.isNotEmpty() && symptoms.isNotEmpty()) {
                        healthViewModel.addReport(diseaseName, symptoms)
                        navController.navigate(ROUTES.Guidance.name)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Next")
            }
        }
    }
}
