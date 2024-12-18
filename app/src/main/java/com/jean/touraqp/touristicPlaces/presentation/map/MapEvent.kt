package com.jean.touraqp.touristicPlaces.presentation.map

import com.google.android.gms.maps.model.Marker

sealed class MapEvent {
    data class OnAddMarkers(val markers: Map<String, Marker?> = mutableMapOf()): MapEvent()
}