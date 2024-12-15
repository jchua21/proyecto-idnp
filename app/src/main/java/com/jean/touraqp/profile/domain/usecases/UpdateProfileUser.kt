package com.jean.touraqp.profile.domain.usecases

import com.jean.touraqp.auth.domain.authentication.model.User
import com.jean.touraqp.core.utils.Result
import com.jean.touraqp.profile.domain.ProfileRepository
import javax.inject.Inject

class UpdateProfileUserUseCase @Inject constructor(
    private val profileRepository: ProfileRepository
) {

    suspend fun execute(user: User): Result<User, Exception>{
        try {
            val userUpdated = profileRepository.updateProfile(user)
            return Result.Success(userUpdated)
        }catch (e: Exception){
            return Result.Error(e)
        }
    }
}