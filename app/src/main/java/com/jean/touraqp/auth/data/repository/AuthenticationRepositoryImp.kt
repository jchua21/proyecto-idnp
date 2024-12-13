package com.jean.touraqp.auth.data.repository

import com.jean.touraqp.auth.data.repository.datasource.UserRemoteDataSource
import com.jean.touraqp.auth.domain.AuthenticationRepository
import com.jean.touraqp.auth.domain.authentication.model.User
import com.jean.touraqp.core.utils.Result
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthenticationRepositoryImp @Inject constructor(
    private val remoteDataSource: UserRemoteDataSource
): AuthenticationRepository {
    override suspend fun registerUser(user: User): Result<User, Exception> {
        return remoteDataSource.registerUser(user)
    }

    override suspend fun loginUser(email: String, password: String): Result<User, Exception> {
        return  remoteDataSource.loginUser(email, password)
    }
}