package com.jean.touraqp.auth.ui.login

data class LoginInputState(val email: String = "", val password: String = "")

data class LoginValidationState(
    val emailError: String? = null,
    val passwordError: String? = null,
)