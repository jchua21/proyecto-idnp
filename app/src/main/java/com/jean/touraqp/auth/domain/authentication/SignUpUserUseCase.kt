package com.jean.touraqp.auth.domain.authentication

import com.jean.touraqp.auth.data.repository.UserRepository
import com.jean.touraqp.auth.domain.authentication.model.User
import com.jean.touraqp.auth.domain.authentication.model.toUserDTO
import com.jean.touraqp.core.ResourceResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SignUpUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    suspend fun execute(user: User): Flow<ResourceResult<User>>{
        return userRepository.signUpUser(user.toUserDTO())
    }
}