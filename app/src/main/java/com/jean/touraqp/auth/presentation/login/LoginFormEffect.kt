package com.jean.touraqp.auth.presentation.login

import com.jean.touraqp.auth.presentation.model.UserUI

sealed  class LoginFormEffect {
    data class OnSuccessUserLogin(val user: UserUI): LoginFormEffect()
    data class OnErrorUserLogin(val message: String): LoginFormEffect()
}