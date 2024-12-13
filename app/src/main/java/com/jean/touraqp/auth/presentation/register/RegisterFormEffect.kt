package com.jean.touraqp.auth.presentation.register

import com.jean.touraqp.auth.presentation.model.UserUI

sealed class RegisterFormEffect {
    data class OnSuccessUserRegistered(val user: UserUI) : RegisterFormEffect()
    data class OnErrorUserRegistered(val message: String) : RegisterFormEffect()
}