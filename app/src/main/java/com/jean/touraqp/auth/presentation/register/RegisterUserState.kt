package com.jean.touraqp.auth.presentation.register

import com.jean.touraqp.auth.presentation.model.UserUI


data class RegisterUserState(
    val username: String = "",
    val usernameError: String? = null,
    val name: String = "",
    val nameError: String? = null,
    val email: String = "",
    val emailError: String? = null,
    val password: String = "",
    val passwordError: String? = null,
    val confirmPassword: String = "",
    val confirmPasswordError: String? = null,
    val isSuccess: Boolean = false,
    val isLoading: Boolean = false,
    val user: UserUI? = null
)
