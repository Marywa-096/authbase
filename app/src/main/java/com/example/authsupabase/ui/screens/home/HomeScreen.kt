package com.example.authsupabase.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.authsupabase.models.ReportedDisease
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
    val userName = userEmail.substringBefore("@").replaceFirstChar { it.uppercase() }
    
    val reports by healthViewModel.reports.collectAsState()
    var showReportsSheet by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        healthViewModel.fetchAllReports()
    }
    
    val backgroundBrush = Brush.verticalGradient(
        colors = listOf(
            MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f),
            MaterialTheme.colorScheme.background
        )
    )

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { 
                    Text(
                        "Health Dashboard", 
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    ) 
                },
                actions = {
                    IconButton(onClick = { showReportsSheet = true }) {
                        BadgedBox(badge = { if(reports.isNotEmpty()) Badge { Text(reports.size.toString()) } }) {
                            Icon(Icons.Default.Notifications, contentDescription = "Alerts")
                        }
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize().background(backgroundBrush).padding(padding)) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp)
            ) {
                // Greeting Section
                Row(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(60.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primary)
                            .clickable { navController.navigate(ROUTES.Profile.name) },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = userName.take(1),
                            color = Color.White,
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(
                            text = "Hello, $userName",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.ExtraBold
                        )
                        Text(
                            text = "Manage your health effectively today",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                Text(
                    text = "Control & Prevention Hub",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 12.dp)
                )

                // Main Pillars Grid
                val dashboardItems = listOf(
                    DashboardItem("Prevention\nTips", Icons.Default.Shield, ROUTES.Prevention, Color(0xFF4CAF50)),
                    DashboardItem("Disease\nManagement", Icons.Default.HealthAndSafety, ROUTES.Management, Color(0xFF2196F3)),
                    DashboardItem("Health\nRecords", Icons.Default.BarChart, ROUTES.HealthRecords, Color(0xFF9C27B0)),
                    DashboardItem("Emergency\nSupport", Icons.Default.Emergency, ROUTES.Emergency, Color(0xFFF44336)),
                    DashboardItem("Nearby\nHospitals", Icons.Default.LocalHospital, ROUTES.NearbyHospitals, Color(0xFFFF9800)),
                    DashboardItem("Health\nGuidance", Icons.AutoMirrored.Filled.MenuBook, ROUTES.Guidance, Color(0xFF795548))
                )

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
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
                
                // Primary Action: Report
                Button(
                    onClick = { navController.navigate(ROUTES.ReportDisease.name) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 24.dp)
                        .height(64.dp),
                    shape = RoundedCornerShape(20.dp),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp)
                ) {
                    Icon(Icons.Default.AddCircleOutline, contentDescription = null)
                    Spacer(Modifier.width(12.dp))
                    Text(
                        "REPORT NEW CASE", 
                        fontWeight = FontWeight.Bold, 
                        letterSpacing = 1.sp,
                        fontSize = 18.sp
                    )
                }
            }
        }

        if (showReportsSheet) {
            ModalBottomSheet(
                onDismissRequest = { showReportsSheet = false }
            ) {
                ReportsContent(reports) { showReportsSheet = false }
            }
        }
    }
}

@Composable
fun ReportsContent(reports: List<ReportedDisease>, onDismiss: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .heightIn(max = 500.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "Disease Reports",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            IconButton(onClick = onDismiss) {
                Icon(Icons.Default.Close, contentDescription = "Close")
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))

        if (reports.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxWidth().padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Text("No reports found", color = Color.Gray)
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(reports) { report ->
                    ReportItem(report)
                }
            }
        }
        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
fun ReportItem(report: ReportedDisease) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    report.diseaseName,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    report.date.split(" ")[0],
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.Gray
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                "Symptoms: ${report.symptoms}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

data class DashboardItem(val title: String, val icon: ImageVector, val route: ROUTES, val color: Color)

@Composable
fun DashboardCard(item: DashboardItem, onClick: () -> Unit) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = Color.White
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Surface(
                modifier = Modifier.size(50.dp),
                shape = CircleShape,
                color = item.color.copy(alpha = 0.15f)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = null,
                        tint = item.color,
                        modifier = Modifier.size(28.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = item.title,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.labelLarge.copy(
                    fontWeight = FontWeight.Bold,
                    lineHeight = 18.sp
                ),
                color = Color.DarkGray
            )
        }
    }
}
