package com.example.authsupabase.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.authsupabase.R
import com.example.authsupabase.network.SupabaseClient
import com.example.authsupabase.ui.navigation.ROUTES
import com.example.authsupabase.ui.screens.records.viewmodel.HealthViewModel
import io.github.jan.supabase.auth.auth

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
            TopAppBar(
                title = { Text("Health Dashboard") },
                actions = {
                    IconButton(onClick = { showReportsSheet = true }) {
                        BadgedBox(badge = { if(reports.isNotEmpty()) Badge { Text(reports.size.toString()) } }) {
                            Icon(Icons.Default.Notifications, contentDescription = "Reports")
                        }
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            // Header Section
            Surface(
                color = MaterialTheme.colorScheme.primaryContainer,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(24.dp)) {
                    Text(
                        text = "Welcome back,",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = userName,
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Dashboard Grid
            val dashboardItems = listOf(
                DashboardItem("Prevention Tips", Icons.Default.Info, ROUTES.Prevention),
                DashboardItem("Disease Management", Icons.Default.Edit, ROUTES.Management),
                DashboardItem("Health Records", Icons.Default.List, ROUTES.HealthRecords),
                DashboardItem("Emergency Help", Icons.Default.Warning, ROUTES.Emergency),
                DashboardItem("Nearby Hospitals", Icons.Default.LocationOn, ROUTES.NearbyHospitals)
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(dashboardItems) { item ->
                    DashboardCard(item) {
                        navController.navigate(item.route.name)
                    }
                }
            }
            
            // Bottom Action
            Button(
                onClick = { navController.navigate(ROUTES.ReportDisease.name) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .height(56.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text("Report a Health Case")
            }
        }

        if (showReportsSheet) {
            ModalBottomSheet(
                onDismissRequest = { showReportsSheet = false },
                sheetState = sheetState
            ) {
                ReportsContent(reports) { showReportsSheet = false }
            }
        }
    }
}

data class DashboardItem(val title: String, val icon: ImageVector, val route: ROUTES)

@Composable
fun DashboardCard(item: DashboardItem, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = item.icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = item.title,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.labelLarge,
                lineHeight = 16.sp
            )
        }
    }
}

@Composable
fun ReportsContent(reports: List<com.example.authsupabase.models.ReportedDisease>, onClose: () -> Unit) {
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
            onClick = onClose,
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
        ) {
            Text("Close")
        }
    }
}
