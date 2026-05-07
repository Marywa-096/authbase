package com.example.authsupabase.ui.screens.authentication.forgotpassword

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
fun ForgotPasswordScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    forgotPasswordViewModel: ForgotPasswordViewModel = viewModel()
) {
    // Acknowledging the ViewModel usage to suppress the unused warning
    val _vm = forgotPasswordViewModel

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Forgot Password Screen")

        Button(onClick = {
            navController.popBackStack()
        }) {
            Text(text = "Back")
        }
    }
}
@Preview(showBackground = true)
@Composable
fun ForgotPasswordScreenPreview() {
    ForgotPasswordScreen(navController = rememberNavController())
}

