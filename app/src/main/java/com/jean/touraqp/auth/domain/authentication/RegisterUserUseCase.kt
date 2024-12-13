package com.jean.touraqp.auth.domain.authentication

import com.jean.touraqp.auth.domain.AuthenticationRepository
import com.jean.touraqp.auth.domain.authentication.model.User
import com.jean.touraqp.core.utils.Result
import javax.inject.Inject

class RegisterUserUseCase @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
) {

    suspend fun execute(user: User): Result<User, Exception>  {
        return  authenticationRepository.registerUser(user)
    }
}