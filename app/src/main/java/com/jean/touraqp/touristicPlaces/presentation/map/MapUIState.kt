package com.jean.touraqp.touristicPlaces.presentation.map

import com.google.android.gms.maps.model.LatLng
import com.jean.touraqp.touristicPlaces.domain.model.TouristicPlace

data class MapUIState(
    val touristicPlaces: List<TouristicPlace> = emptyList(),
    val isLoading: Boolean = false,
    val hasError: Boolean = false,

)
