package com.grigor.nearme.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {
    enum class AuthenticationState {
        UNAUTHENTICATED, // Initial state, the user needs to authenticate
        AUTHENTICATED, // The user has authenticated successfully
        INVALID_AUTHENTICATION  // Authentication failed
    }

    val authenticationState = MutableLiveData<AuthenticationState>()
    val userEmail: String

    init {
        authenticationState.value = AuthenticationState.UNAUTHENTICATED
        userEmail = ""
    }

//    fun refUseAuthentication() {
//        authenticationState.value = AuthenticationState.UNAUTHENTICATED
//    }

    /**
     * Check authentication.
     */
    fun authenticate(userEmail: String) {
        if (emailIsValidForUser(userEmail)) {
            authenticationState.value = AuthenticationState.AUTHENTICATED
            Log.i("LoginViewmodel","Authenticated!")
        } else {
            authenticationState.value = AuthenticationState.INVALID_AUTHENTICATION
            Log.i("LoginViewModel", "Email  isn't correct!")
        }
    }

    /**
     * Check email validation.
     */
    private fun emailIsValidForUser(userEmail: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()
    }
}