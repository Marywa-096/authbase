package com.example.authsupabase.ui.screens.authentication.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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

@Composable
fun RegisterScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    registerViewModel: RegisterViewModel = viewModel()
){
    val isLoading by registerViewModel.isLoading.collectAsState()
    val responseMessage by registerViewModel.message.collectAsState()
    var password by remember { mutableStateOf(TextFieldValue("")) }
    var email by remember{ mutableStateOf(TextFieldValue("")) }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ) {
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(text = "password") },
            maxLines = 1
        )
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text(text = "email") },
            maxLines = 1
        )
        HorizontalDivider()
        Text(text = responseMessage)
        Text(text = isLoading.toString())
        HorizontalDivider()
        if (isLoading) {
            CircularProgressIndicator()
        } else {
            OutlinedButton(
                onClick = {
                    val user = UserModel(
                        email = email.text,
                        password = password.text
                    )
                    registerViewModel.registerUser(user)
                }
            ) {
                Text(text = "create account")
            }
        }

        HorizontalDivider()

        OutlinedButton(onClick = { navController.popBackStack() }) {
            Text(text = "Already have an account? Login")
        }
    }
}
