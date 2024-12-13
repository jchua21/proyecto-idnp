package com.jean.touraqp.auth.domain.authentication

import com.jean.touraqp.auth.domain.AuthenticationRepository
import com.jean.touraqp.auth.domain.authentication.model.User
import com.jean.touraqp.auth.domain.authentication.model.toUserUI
import com.jean.touraqp.auth.presentation.model.UserUI
import com.jean.touraqp.core.utils.ResourceResult
import com.jean.touraqp.core.utils.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LogInUserUseCase @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
) {

    suspend fun execute(email: String, password: String): Result<User, Exception>  {
        return authenticationRepository.loginUser(email, password)
    }
}