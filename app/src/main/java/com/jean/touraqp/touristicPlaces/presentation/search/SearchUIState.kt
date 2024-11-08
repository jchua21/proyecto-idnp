package com.jean.touraqp.touristicPlaces.presentation.search

import com.jean.touraqp.touristicPlaces.domain.model.TouristicPlace

data class SearchUIState(
    val touristicPlaceList: List<TouristicPlace> = emptyList(),
    val isLoading: Boolean = false,
    val hasError: Boolean = false,
    val searchQuery: String = ""
)