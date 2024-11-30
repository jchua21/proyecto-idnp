package com.jean.touraqp.touristicPlaces.presentation.shared

sealed class TouristicPlaceEffect {
    data object OnSelectTouristicPlace: TouristicPlaceEffect()
}