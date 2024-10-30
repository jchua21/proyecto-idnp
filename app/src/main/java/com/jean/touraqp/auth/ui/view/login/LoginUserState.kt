package com.jean.touraqp.auth.ui.view.login

data class LoginInputState(val username: String = "", val password: String = "")

data class LoginValidationState(
    val usernameError: String? = null,
    val passwordError: String? = null,
)