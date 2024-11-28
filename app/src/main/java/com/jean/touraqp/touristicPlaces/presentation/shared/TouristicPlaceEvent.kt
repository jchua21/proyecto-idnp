package com.jean.touraqp.touristicPlaces.presentation.shared

sealed class TouristicPlaceEvent{
    data class OnSelectTouristicPlace(val id: String): TouristicPlaceEvent()
}