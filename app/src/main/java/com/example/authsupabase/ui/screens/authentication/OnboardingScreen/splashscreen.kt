package com.example.authsupabase.ui.screens.authentication.onboardingscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.authsupabase.R
import com.example.authsupabase.ui.navigation.ROUTES
import kotlinx.coroutines.delay

@Composable
fun Splashscreen(navController: NavHostController) {
    LaunchedEffect(key1 = true) {
        delay(3000)
        navController.navigate(ROUTES.Register.name) {
            popUpTo(ROUTES.Splash.name) { inclusive = true }
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "CARE-FORTRESS",
                color = MaterialTheme.colorScheme.primary,
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "WELCOME",
                color = MaterialTheme.colorScheme.secondary,
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(24.dp))
            Image(
                painter = painterResource(id = R.drawable.health),
                contentDescription = "company logo",
                modifier = Modifier
                    .height(200.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Secure your data",
                color = MaterialTheme.colorScheme.tertiary,
                fontSize = 24.sp,
                fontWeight = FontWeight.Light
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SplashscreenPreview() {
    Splashscreen(navController = rememberNavController())
}
