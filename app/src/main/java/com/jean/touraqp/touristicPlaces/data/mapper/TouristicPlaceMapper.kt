package com.jean.touraqp.touristicPlaces.data.mapper

import com.jean.touraqp.touristicPlaces.data.remote.dto.TouristicPlaceDto
import com.jean.touraqp.touristicPlaces.domain.model.TouristicPlace

fun TouristicPlaceDto.toTouristicPlace() : TouristicPlace {
    return TouristicPlace(
        name = name,
        description = description
    )
}