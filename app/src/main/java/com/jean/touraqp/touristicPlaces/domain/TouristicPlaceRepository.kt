package com.jean.touraqp.touristicPlaces.domain

import com.jean.touraqp.core.utils.Result
import com.jean.touraqp.touristicPlaces.domain.model.TouristicPlace

interface TouristicPlaceRepository {

    suspend fun getAllTouristicPlaces(fetchFromNetwork: Boolean = true): List<TouristicPlace>
}