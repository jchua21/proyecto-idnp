package com.jean.touraqp.auth.di

import com.jean.touraqp.auth.data.repository.UserRepositoryImp
import com.jean.touraqp.auth.domain.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindUserRepository(
        authenticationRepository: UserRepositoryImp
    ): UserRepository
}