package com.jean.touraqp.touristicPlaces.presentation.shared

import com.jean.touraqp.touristicPlaces.domain.model.ReviewWithUser

sealed class TouristicPlaceEffect {
    data object OnSelectTouristicPlace: TouristicPlaceEffect()
}