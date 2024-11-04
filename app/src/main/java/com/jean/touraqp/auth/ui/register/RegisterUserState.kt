package com.jean.touraqp.auth.ui.register

import com.jean.touraqp.auth.ui.model.UserUI


data class RegisterValidationState(
    val usernameError: String? = null,
    val nameError: String? = null,
    val emailError: String? = null,
    val passwordError: String? = null,
    val confirmPasswordError: String? = null
)

data class RegisterResultState(
    val isSuccess: Boolean = false,
    val isLoading: Boolean = false,
    val resultMessage: String? = null,
    val user: UserUI? = null
)