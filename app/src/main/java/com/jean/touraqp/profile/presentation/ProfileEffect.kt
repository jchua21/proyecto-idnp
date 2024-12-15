package com.jean.touraqp.profile.presentation

import com.jean.touraqp.auth.presentation.model.UserUI

sealed class ProfileEffect {
    data class OnSuccessProfileUpdated(val userUpdated: UserUI): ProfileEffect()
    data class OnErrorProfileUpdated(val message: String): ProfileEffect()
}