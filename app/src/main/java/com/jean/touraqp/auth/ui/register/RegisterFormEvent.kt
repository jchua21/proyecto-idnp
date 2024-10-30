package com.jean.touraqp.auth.ui.register

sealed class RegisterFormEvent {
    data class UsernameChanged(val username: String): RegisterFormEvent()
    data class NameChanged(val name: String): RegisterFormEvent()
    data class EmailChanged(val email: String): RegisterFormEvent()
    data class PasswordChanged(val password: String): RegisterFormEvent()
    data class ConfirmPasswordChanged(val confirmPassword: String): RegisterFormEvent()
    object Submit : RegisterFormEvent()
}