package com.jean.touraqp.core.di

import android.content.Context
import androidx.room.Room
import com.jean.touraqp.core.TourAqpDB
import com.jean.touraqp.touristicPlaces.data.local.dao.TouristicPlaceDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    const val TOUR_AQP_DB_NAME = "tourAQP"

    @Singleton
    @Provides
    fun provideRoom(@ApplicationContext context: Context): TourAqpDB =
        Room.databaseBuilder(context, TourAqpDB::class.java, TOUR_AQP_DB_NAME).build()

    @Singleton
    @Provides
    fun provideTouristicPlaceDao(db: TourAqpDB): TouristicPlaceDao = db.getTouristicPlaceDao()
}