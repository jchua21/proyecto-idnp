package com.jean.touraqp.auth.ui.login

import com.jean.touraqp.auth.ui.model.UserUI

data class LoginInputState(val email: String = "", val password: String = "")

data class LoginValidationState(
    val emailError: String? = null,
    val passwordError: String? = null,
)

data class LoginResultState (
    val isSuccess: Boolean = false,
    val isLoading: Boolean = false,
    val resultMessage: String? = null,
    val user: UserUI? = null
)