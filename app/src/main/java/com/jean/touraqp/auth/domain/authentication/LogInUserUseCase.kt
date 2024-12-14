package com.jean.touraqp.auth.domain.authentication

import com.jean.touraqp.auth.domain.UserRepository
import com.jean.touraqp.auth.domain.authentication.model.User
import com.jean.touraqp.core.utils.Result
import javax.inject.Inject

class LogInUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    suspend fun execute(email: String, password: String): Result<User, Exception>  {
        return userRepository.loginUser(email, password)
    }
}