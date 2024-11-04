package com.jean.touraqp.auth.domain.authentication

import com.jean.touraqp.auth.data.repository.UserRepository
import com.jean.touraqp.auth.domain.authentication.model.UserDomain
import com.jean.touraqp.auth.domain.authentication.model.toUserDTO
import com.jean.touraqp.auth.domain.authentication.model.toUserUI
import com.jean.touraqp.auth.ui.model.UserUI
import com.jean.touraqp.core.utils.ResourceResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SignUpUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    suspend fun execute(user: UserDomain): Flow<ResourceResult<UserUI>> = flow {
        userRepository.signUpUser(user.toUserDTO())
            .map() { result -> when (result) {
                    is ResourceResult.Success -> {
                        // Convert UserDomain to UserUI
                        val userUI = result.data?.toUserUI()
                        return@map ResourceResult.Success(data = userUI, message = result.message)
                    }
                    is ResourceResult.Error -> {
                        return@map  ResourceResult.Error(message = result.message)
                    }
                    is ResourceResult.Loading -> {
                        return@map (ResourceResult.Loading())
                    }
                }
            }
            .collect(){ emit(it)}
    }
}