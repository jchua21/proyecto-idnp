package com.jean.touraqp.touristicPlaces.presentation.map

import com.google.android.gms.maps.model.Marker

data class MapState(
    val markers: Map<String, Marker?> = mutableMapOf(),
    val idMarkerSelected:String? = null,
){
    val hasIdMarkerSelected
        get() = idMarkerSelected != null
}