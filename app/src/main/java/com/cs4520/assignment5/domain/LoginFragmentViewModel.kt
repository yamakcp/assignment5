package com.cs4520.assignment5.domain

import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.flow.MutableStateFlow

class LoginFragmentViewModel: ViewModel() {
    var usernameTxt: MutableState<String> = mutableStateOf("")
    var passwordTxt: MutableState<String> = mutableStateOf("")
    var errorMssgTxt: MutableState<String> = mutableStateOf("")


    public fun setUsernameText(text: String) {
        usernameTxt.value = text
        clearError()
    }


    public fun setPasswordText(text: String) {
        passwordTxt.value = text
        clearError()
    }


    private fun clearInput() {
        usernameTxt.value = ""
        passwordTxt.value = ""
    }


    private fun clearError() {
        errorMssgTxt.value = ""
    }


     //username: "admin"
     //password: "admin"
    public fun login(): Boolean {
         if (usernameTxt.value.isEmpty() || passwordTxt.value.isEmpty()) {
             errorMssgTxt.value = "Please enter your username and/or password!"
         }

         else if (usernameTxt.value == "admin" && passwordTxt.value == "admin") {
            clearInput()
            return true
        }

         errorMssgTxt.value = "Invalid credentials. Please try again :)"
         clearInput()

        return false
    }
}
