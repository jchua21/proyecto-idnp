package com.jean.touraqp.touristicPlaces.domain

import com.jean.touraqp.core.utils.Result
import com.jean.touraqp.touristicPlaces.domain.model.TouristicPlace

interface TouristicPlaceRepository {

    suspend fun getTouristicPlaces(fetchFromNetwork: Boolean = true, query: String = ""): List<TouristicPlace>
}