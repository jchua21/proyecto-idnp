package com.jean.touraqp.auth.presentation.login

sealed  class LoginFormEffect {
    object OnSuccessUserLogin: LoginFormEffect()
    data class OnErrorUserLogin(val message: String): LoginFormEffect()
}