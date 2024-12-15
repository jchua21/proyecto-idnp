package com.jean.touraqp.profile.presentation

import com.jean.touraqp.auth.presentation.model.UserUI

sealed class ProfileEvent {
    data class OnEmailChanged(val email: String) : ProfileEvent()
    data class OnUsernameChanged(val username: String) : ProfileEvent()
    data class OnNameChanged(val name: String) : ProfileEvent()
    data class OnUpdateProfile(val userId: String) : ProfileEvent()
    data class OnSyncProfile(val email: String, val username: String, val name: String) :
        ProfileEvent()
}