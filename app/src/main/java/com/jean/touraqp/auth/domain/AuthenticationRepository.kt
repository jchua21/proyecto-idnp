package com.jean.touraqp.auth.domain

import com.jean.touraqp.auth.data.remote.dto.UserDto
import com.jean.touraqp.auth.domain.authentication.model.User
import com.jean.touraqp.core.utils.Result

interface AuthenticationRepository {
    suspend fun registerUser(user: User) : Result<User, Exception>
    suspend fun loginUser(email: String, password: String): Result<User, Exception>
}