package com.example.authsupabase.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.authsupabase.ui.screens.authentication.onboardingscreen.Splashscreen
import com.example.authsupabase.ui.screens.authentication.forgotpassword.ForgotPasswordScreen
import com.example.authsupabase.ui.screens.authentication.login.LoginScreen
import com.example.authsupabase.ui.screens.authentication.register.RegisterScreen
import com.example.authsupabase.ui.screens.home.HomeScreen

@Composable
fun AppNavigation(
    navController: NavHostController,
    modifier: Modifier
){
    NavHost(
        navController = navController,
        startDestination = ROUTES.Splash.name
    ){
        composable(ROUTES.Splash.name) { Splashscreen(navController = navController) }
        composable(ROUTES.Login.name) { LoginScreen( navController= navController,modifier = modifier) }
        composable(ROUTES.ForgotPassword.name) { ForgotPasswordScreen( navController= navController,modifier = modifier) }
        composable(ROUTES.Register.name) { RegisterScreen( navController= navController,modifier = modifier) }
        composable(ROUTES.Home.name) { HomeScreen() }
    }
}