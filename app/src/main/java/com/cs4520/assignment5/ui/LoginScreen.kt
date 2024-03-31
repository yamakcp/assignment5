package com.cs4520.assignment5.ui

import com.cs4520.assignment5.Screen
import com.cs4520.assignment5.domain.LoginFragmentViewModel

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavHostController

@Composable
fun LoginScreen(
    navHostController: NavHostController,
    loginFragmentViewModel: LoginFragmentViewModel = viewModel()
) {
    val context = LocalContext.current

    val errorMessageText by loginFragmentViewModel.errorMssgTxt

    val usernameText by loginFragmentViewModel.usernameTxt

    val passwordText by loginFragmentViewModel.passwordTxt

    if (errorMessageText.isNotEmpty()) {
        Toast.makeText(context, errorMessageText, Toast.LENGTH_LONG).show()
    }

    Box(
        modifier = Modifier.fillMaxSize().background(Color.White),
        contentAlignment = Alignment.Center
    )
    {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        )
        {
            TextField(value = usernameText,
                onValueChange = { loginFragmentViewModel.setUsernameText(it) },
                label = { Text("Username") }, singleLine = true,
            )

            TextField(value = passwordText,
                onValueChange = { loginFragmentViewModel.setPasswordText(it) },
                label = { Text("Password") }, singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )

            Button(
                onClick = {
                    if (loginFragmentViewModel.login()) {
                        navHostController.navigate(Screen.PRODUCT_LIST.name)
                    }
                }
            ) {
                Text(text = "Login")
            }
        }
    }
}

@Preview
@Composable
fun LoginFragmentPreview() {
    MaterialTheme{
        LoginScreen(navHostController = rememberNavController())
    }
}