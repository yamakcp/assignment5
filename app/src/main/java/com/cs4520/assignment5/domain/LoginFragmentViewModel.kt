package com.cs4520.assignment5.domain

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class LoginFragmentViewModel: ViewModel() {
    var usernameTxt: MutableState<String> = mutableStateOf("")
    var passwordTxt: MutableState<String> = mutableStateOf("")
    var errorMssgTxt: MutableState<String> = mutableStateOf("")

    fun setPasswordText(text: String) {
        passwordTxt.value = text
        clearError()
    }
    fun setUsernameText(text: String) {
        usernameTxt.value = text
        clearError()
    }

    private fun clearError() {
        errorMssgTxt.value = ""
    }

    private fun clearInput() {
        usernameTxt.value = ""
        passwordTxt.value = ""
    }


     //username: "admin"
     //password: "admin"
    fun login(): Boolean {
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
