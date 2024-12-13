package com.jean.touraqp.auth.presentation.login

import com.jean.touraqp.auth.presentation.model.UserUI

data class LoginUserState(
    val email: String = "",
    val emailError: String? = null,
    val password: String = "",
    val passwordError: String? = null,
    val isSuccess: Boolean = false,
    val isLoading: Boolean = false,
    val user: UserUI? = null
)
