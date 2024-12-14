package com.jean.touraqp.touristicPlaces.di

import com.jean.touraqp.touristicPlaces.data.repository.touristicPlace.TouristicPlaceRepositoryImp
import com.jean.touraqp.touristicPlaces.data.repository.review.ReviewRepositoryImp
import com.jean.touraqp.touristicPlaces.domain.ReviewRepository
import com.jean.touraqp.touristicPlaces.domain.TouristicPlaceRepository
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
    abstract fun bindTouristicPlace(
        touristicPlaceRepositoryImp: TouristicPlaceRepositoryImp
    ): TouristicPlaceRepository

    @Binds
    @Singleton
    abstract fun bindReview(
        reviewRepositoryImp: ReviewRepositoryImp
    ): ReviewRepository
}