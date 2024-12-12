package com.jean.touraqp.touristicPlaces.domain

import com.jean.touraqp.core.utils.ResourceResult
import com.jean.touraqp.core.utils.Result
import com.jean.touraqp.touristicPlaces.data.remote.dto.ReviewCreationDto
import com.jean.touraqp.touristicPlaces.data.remote.dto.ReviewDto
import com.jean.touraqp.touristicPlaces.domain.model.Review
import com.jean.touraqp.touristicPlaces.domain.model.TouristicPlace
import kotlinx.coroutines.flow.Flow

interface TouristicPlaceRepository {

    suspend fun getAllTouristicPlaces(fetchFromNetwork: Boolean = true): Result<List<TouristicPlace>, Exception>
}