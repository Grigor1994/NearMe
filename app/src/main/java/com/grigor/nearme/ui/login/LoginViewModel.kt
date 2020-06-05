package com.grigor.nearme.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.grigor.nearme.data.firebase.FirebaseUserLiveData

class LoginViewModel : ViewModel() {


    enum class AuthenticationState {
        UNAUTHENTICATED, // Initial state, the user needs to authenticate
        AUTHENTICATED, // The user has authenticated successfully
    }


    /**
     * Check authentication.
     */
    val authenticationState = FirebaseUserLiveData().map { user ->
        if (user != null) {
            AuthenticationState.AUTHENTICATED
        } else {
            AuthenticationState.UNAUTHENTICATED
        }
    }

}