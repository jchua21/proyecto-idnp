package com.jean.touraqp.touristicPlaces.data.mapper

import com.jean.touraqp.touristicPlaces.data.remote.dto.TouristicPlaceDto
import com.jean.touraqp.touristicPlaces.domain.model.TouristicPlace

fun TouristicPlaceDto.toTouristicPlace(id: String) : TouristicPlace {
    return TouristicPlace(
        id = id,
        name = name,
        description = description,
        imageUrl = imageUrl
    )
}