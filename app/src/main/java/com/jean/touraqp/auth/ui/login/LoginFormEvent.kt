package com.jean.touraqp.auth.ui.login

sealed class LoginFormEvent {
    data class  UsernameChanged(val username: String): LoginFormEvent()
    data class PasswordChanged(val password: String): LoginFormEvent()
    object Submit: LoginFormEvent()
}