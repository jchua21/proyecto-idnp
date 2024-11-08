package com.jean.touraqp.touristicPlaces.domain

import com.jean.touraqp.core.utils.ResourceResult
import com.jean.touraqp.touristicPlaces.domain.model.TouristicPlace
import kotlinx.coroutines.flow.Flow

interface TouristicPlaceRepository {

    suspend fun getAllTouristicPlaces(): Flow<ResourceResult<List<TouristicPlace>>>
    suspend fun getTouristicPlaceDetail(id: String): Flow<ResourceResult<TouristicPlace>>
}