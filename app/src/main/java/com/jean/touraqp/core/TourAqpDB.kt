package com.jean.touraqp.core

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jean.touraqp.touristicPlaces.data.local.dao.TouristicPlaceDao
import com.jean.touraqp.touristicPlaces.data.local.entities.TouristicPlaceEntity

@Database(entities = [TouristicPlaceEntity::class], version = 1)
abstract class TourAqpDB: RoomDatabase() {

    abstract fun getTouristicPlaceDao(): TouristicPlaceDao

}