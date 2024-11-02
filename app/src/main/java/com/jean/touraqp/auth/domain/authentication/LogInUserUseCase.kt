package com.jean.touraqp.auth.domain.authentication

import com.jean.touraqp.auth.data.repository.UserRepository
import com.jean.touraqp.auth.domain.authentication.model.User
import com.jean.touraqp.core.ResourceResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LogInUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    suspend fun execute(email: String, password: String): Flow<ResourceResult<User>> {
        return userRepository.logInUser(email = email, password = password)
    }
}