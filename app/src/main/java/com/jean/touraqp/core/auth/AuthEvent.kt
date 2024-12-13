package com.jean.touraqp.core.auth

import com.jean.touraqp.auth.presentation.model.UserUI

sealed class AuthEvent(){
    data class OnUserLoggedIn(val user: UserUI): AuthEvent()
    object OnUserLoggedOut: AuthEvent()
}