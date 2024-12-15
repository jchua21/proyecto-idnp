package com.jean.touraqp.profile.di

import com.jean.touraqp.auth.data.repository.UserRepositoryImp
import com.jean.touraqp.profile.domain.ProfileRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindProfile(profileRepository: UserRepositoryImp): ProfileRepository
}
