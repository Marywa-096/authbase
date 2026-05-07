package com.example.authsupabase.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.authsupabase.R
import com.example.authsupabase.network.SupabaseClient
import com.example.authsupabase.ui.navigation.ROUTES
import io.github.jan.supabase.auth.auth

import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.authsupabase.ui.screens.records.viewmodel.HealthViewModel

import androidx.compose.ui.text.font.FontWeight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
    healthViewModel: HealthViewModel = viewModel()
) {
    val userEmail = SupabaseClient.client.auth.currentUserOrNull()?.email ?: "User"
    val userName = userEmail.substringBefore("@")
    
    val reports by healthViewModel.reports.collectAsState()
    var showReportsSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()

    LaunchedEffect(Unit) {
        healthViewModel.fetchAllReports()
    }
    
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Home") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(24.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Hello, $userName!",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Welcome to your Health Assistant. We are here to help you manage and prevent lifestyle diseases.",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(bottom = 16.dp),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )

            // Prominent button to view reports
            OutlinedButton(
                onClick = { showReportsSheet = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(painterResource(id = android.R.drawable.ic_menu_agenda), contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text("View Community Reports (${reports.size})")
            }

            Image(
                painter = painterResource(id = R.drawable.health),
                contentDescription = "Health Image",
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(16.dp)
            )

            Button(
                onClick = { navController.navigate(ROUTES.ReportDisease.name) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text(
                    text = "Next",
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }

        if (showReportsSheet) {
            ModalBottomSheet(
                onDismissRequest = { showReportsSheet = false },
                sheetState = sheetState
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .padding(bottom = 32.dp)
                ) {
                    Text(
                        "Community Reported Cases",
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    HorizontalDivider()
                    
                    if (reports.isEmpty()) {
                        Box(modifier = Modifier.fillMaxWidth().height(200.dp), contentAlignment = Alignment.Center) {
                            Text("No reports available yet.")
                        }
                    } else {
                        androidx.compose.foundation.lazy.LazyColumn(
                            modifier = Modifier.fillMaxHeight(0.6f)
                        ) {
                            items(reports.size) { index ->
                                val report = reports[index]
                                Card(
                                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                                ) {
                                    Column(modifier = Modifier.padding(16.dp)) {
                                        Text(report.diseaseName, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                                        Text("Symptoms: ${report.symptoms}", style = MaterialTheme.typography.bodyMedium)
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text("Reported on: ${report.date}", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.outline)
                                    }
                                }
                            }
                        }
                    }
                    
                    Button(
                        onClick = { showReportsSheet = false },
                        modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
                    ) {
                        Text("Close")
                    }
                }
            }
        }
    }
}
