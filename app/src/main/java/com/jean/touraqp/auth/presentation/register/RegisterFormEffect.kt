package com.jean.touraqp.auth.presentation.register

sealed class RegisterFormEffect {
    object OnSuccessUserRegistered : RegisterFormEffect()
    data class OnErrorUserRegistered(val message: String) : RegisterFormEffect()
}