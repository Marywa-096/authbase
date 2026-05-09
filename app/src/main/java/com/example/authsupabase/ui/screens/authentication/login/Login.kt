package com.example.authsupabase.ui.screens.authentication.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.authsupabase.models.UserModel
import com.example.authsupabase.ui.navigation.ROUTES

@Composable
fun LoginScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    loginViewModel: LoginViewModel = viewModel()
){
    val isLoading by loginViewModel.isLoading.collectAsState()
    val responseMessage by loginViewModel.message.collectAsState()
    val isLoggedIn by loginViewModel.isLoggedIn.collectAsState()
    var password by remember { mutableStateOf(TextFieldValue("")) }
    var email by remember{ mutableStateOf(TextFieldValue("")) }
    var  fullName by remember { mutableStateOf(TextFieldValue("")) }
    var contacts by remember { mutableStateOf(TextFieldValue("")) }

    LaunchedEffect(isLoggedIn) {
        if (isLoggedIn) {
            navController.navigate(ROUTES.Home.name) {
                popUpTo(ROUTES.Login.name) { inclusive = true }
            }
        }
    }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ) {
        Text(text = "Login Screen")
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text(text = "email") },
            maxLines = 1
        )
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(text = "password") },
            maxLines = 1
        )
        HorizontalDivider()
        Text(text = responseMessage)
        
        if (isLoading) {
            CircularProgressIndicator()
        } else {
            OutlinedButton(
                onClick = {
                    val user = UserModel(
                        email = email.text,
                        password = password.text
                    )
                    loginViewModel.loginUser(user)
                }
            ) {
                Text(text = "Login")
            }
        }

        HorizontalDivider()

        OutlinedButton(onClick = { navController.navigate(ROUTES.Register.name) }) {
            Text(text = "Don't have an account? Register")
        }
        
        OutlinedButton(onClick = { navController.navigate(ROUTES.ForgotPassword.name) }) {
            Text(text = "Forgot Password?")
        }
    }
}
