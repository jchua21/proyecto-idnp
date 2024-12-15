package com.jean.touraqp.profile.domain

import com.jean.touraqp.auth.domain.authentication.model.User

interface ProfileRepository {
    suspend fun updateProfile(user: User): User
}