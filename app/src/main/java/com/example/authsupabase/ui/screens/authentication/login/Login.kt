package com.example.authsupabase.ui.screens.authentication.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
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

    LoginContent(
        navController = navController,
        modifier = modifier,
        isLoading = isLoading,
        responseMessage = responseMessage,
        isLoggedIn = isLoggedIn,
        onLoginClick = { email, password ->
            val user = UserModel(email = email, password = password)
            loginViewModel.loginUser(user)
        }
    )
}

@Composable
fun LoginContent(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    responseMessage: String,
    isLoggedIn: Boolean,
    onLoginClick: (String, String) -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var validationError by remember { mutableStateOf("") }

    LaunchedEffect(isLoggedIn) {
        if (isLoggedIn) {
            navController.navigate(ROUTES.Home.name) {
                popUpTo(ROUTES.Login.name) { inclusive = true }
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "LOGIN",
            fontSize = 32.sp,
            color = Color.Black,
            fontFamily = FontFamily.Cursive,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Sign in to manage your health",
            fontSize = 18.sp,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it; validationError = "" },
            label = { Text(text = "Email") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = "email icon"
                )
            },
            modifier = Modifier.fillMaxWidth(),
            maxLines = 1
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = password,
            onValueChange = { password = it; validationError = "" },
            label = { Text(text = "Password") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "password icon"
                )
            },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            maxLines = 1
        )
        Spacer(modifier = Modifier.height(24.dp))

        val displayMessage = validationError.ifEmpty { responseMessage }
        if (displayMessage.isNotEmpty()) {
            Text(
                text = displayMessage,
                color = Color.Red,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        if (isLoading) {
            CircularProgressIndicator(color = Color.Black)
        } else {
            Button(
                onClick = {
                    if (email.isEmpty() || password.isEmpty()) {
                        validationError = "All fields are required"
                    } else {
                        onLoginClick(email, password)
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "LOGIN",
                    fontSize = 24.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Cursive
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(onClick = { navController.navigate(ROUTES.Register.name) }) {
            Text(text = "Don't have an account? Register here")
        }
        
        TextButton(onClick = { navController.navigate(ROUTES.ForgotPassword.name) }) {
            Text(text = "Forgot Password?")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    LoginContent(
        navController = rememberNavController(),
        isLoading = false,
        responseMessage = "",
        isLoggedIn = false,
        onLoginClick = { _, _ -> }
    )
}
