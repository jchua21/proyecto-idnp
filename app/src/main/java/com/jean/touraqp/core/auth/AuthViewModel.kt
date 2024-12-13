package com.jean.touraqp.core.auth

import androidx.lifecycle.ViewModel
import com.jean.touraqp.auth.presentation.model.UserUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor() : ViewModel(){

    private val _state = MutableStateFlow(UserUI())
    val state = _state.asStateFlow()

    private fun onUserLoggedIn(user: UserUI){
        _state.update {
            user
        }
    }

    private fun onUserLoggedOut(){
        _state.update { UserUI() }
    }

    fun onEvent(e : AuthEvent){
        when(e){
            is AuthEvent.OnUserLoggedIn -> {
                onUserLoggedIn(e.user)
            }
            AuthEvent.OnUserLoggedOut -> {
                onUserLoggedOut()
            }
        }
    }
}