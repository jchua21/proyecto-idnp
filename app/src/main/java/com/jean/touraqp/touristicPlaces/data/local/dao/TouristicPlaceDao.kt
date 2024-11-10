package com.jean.touraqp.touristicPlaces.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jean.touraqp.touristicPlaces.data.local.entities.TouristicPlaceEntity

@Dao
interface TouristicPlaceDao {

    @Query("SELECT * FROM touristic_place")
    suspend fun getAllTouristicPlaces(): List<TouristicPlaceEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(touristicPlaces: List<TouristicPlaceEntity>)

    @Query("SELECT * FROM touristic_place WHERE id == :id")
    suspend fun getTouristicPlaceById(id: String): TouristicPlaceEntity
}