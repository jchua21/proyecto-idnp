package com.jean.touraqp.auth.di

import com.jean.touraqp.auth.data.utils.PasswordHasher
import com.lambdapioneer.argon2kt.Argon2Kt
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SecurityModule {

    @Singleton
    @Provides
    fun provideArgon(): Argon2Kt {
        return Argon2Kt()
    }
}