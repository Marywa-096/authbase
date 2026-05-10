package com.example.authsupabase.ui.screens.management

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.authsupabase.ui.screens.records.viewmodel.HealthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManagementScreen(
    navController: NavHostController,
    healthViewModel: HealthViewModel = viewModel()
) {
    var bloodPressure by remember { mutableStateOf("") }
    var sugarLevel by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { 
                    Text(
                        "Health Tracking", 
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                    ) 
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        val backgroundBrush = Brush.verticalGradient(
            colors = listOf(
                MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f),
                MaterialTheme.colorScheme.surface
            )
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundBrush)
                .padding(padding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Daily Vitals",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.ExtraBold
                )
                
                Text(
                    text = "Consistently tracking your metrics leads to better health outcomes.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = 8.dp, bottom = 32.dp)
                )

                ElevatedCard(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.elevatedCardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        verticalArrangement = Arrangement.spacedBy(24.dp)
                    ) {
                        VitalInputStyled(
                            value = bloodPressure,
                            onValueChange = { bloodPressure = it },
                            label = "Blood Pressure",
                            placeholder = "120/80",
                            unit = "mmHg",
                            icon = Icons.Default.Favorite,
                            iconColor = Color(0xFFE91E63) // Pink/Red
                        )

                        VitalInputStyled(
                            value = sugarLevel,
                            onValueChange = { sugarLevel = it },
                            label = "Sugar Level",
                            placeholder = "5.6",
                            unit = "mg/dL",
                            icon = Icons.Default.Info,
                            iconColor = Color(0xFF4CAF50) // Green
                        )
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                Button(
                    onClick = {
                        if (bloodPressure.isNotEmpty()) {
                            healthViewModel.addRecord(
                                type = "Blood Pressure",
                                value = bloodPressure,
                                unit = "mmHg"
                            )
                        }
                        if (sugarLevel.isNotEmpty()) {
                            healthViewModel.addRecord(
                                type = "Sugar Level",
                                value = sugarLevel,
                                unit = "mg/dL"
                            )
                        }
                        navController.popBackStack()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(16.dp),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
                ) {
                    Text(
                        "SAVE RECORDS", 
                        fontSize = 18.sp, 
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.2.sp
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun VitalInputStyled(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String,
    unit: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconColor: Color
) {
    Column {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconColor,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = label,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(placeholder) },
            suffix = { 
                Text(
                    text = unit, 
                    style = MaterialTheme.typography.bodySmall, 
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(end = 8.dp)
                ) 
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface
            )
        )
    }
}
