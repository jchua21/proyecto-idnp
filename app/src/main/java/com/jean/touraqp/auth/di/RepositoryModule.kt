package com.jean.touraqp.auth.di

import com.jean.touraqp.auth.data.repository.AuthenticationRepositoryImp
import com.jean.touraqp.auth.domain.AuthenticationRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindAuthenticationRepository(
        authenticationRepository: AuthenticationRepositoryImp
    ): AuthenticationRepository
}