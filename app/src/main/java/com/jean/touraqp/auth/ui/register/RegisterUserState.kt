package com.jean.touraqp.auth.ui.register

data class RegisterInputState(
    var username: String = "",
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
)

data class RegisterValidationState(
    val usernameError: String? = null,
    val nameError: String? = null,
    val emailError: String? = null,
    val passwordError: String? = null,
    val confirmPasswordError: String? = null

)