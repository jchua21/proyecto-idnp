package com.jean.touraqp.auth.data.repository

import com.jean.touraqp.auth.data.repository.datasource.UserRemoteDataSource
import com.jean.touraqp.auth.domain.UserRepository
import com.jean.touraqp.auth.domain.authentication.model.User
import com.jean.touraqp.core.utils.Result
import com.jean.touraqp.profile.domain.ProfileRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImp @Inject constructor(
    private val remoteDataSource: UserRemoteDataSource
): UserRepository, ProfileRepository {
    override suspend fun registerUser(user: User): Result<User, Exception> {
        return remoteDataSource.registerUser(user)
    }

    override suspend fun loginUser(email: String, password: String): Result<User, Exception> {
        return  remoteDataSource.loginUser(email, password)
    }

    override suspend fun getUserById(userId: String): User {
        return remoteDataSource.getUserById(userId)
    }

    override suspend fun updateProfile(user: User): User {
         return remoteDataSource.updateUser(user)
    }
}