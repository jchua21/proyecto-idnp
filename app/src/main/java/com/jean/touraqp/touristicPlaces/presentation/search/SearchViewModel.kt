package com.jean.touraqp.touristicPlaces.presentation.search

import androidx.lifecycle.ViewModel
import com.jean.touraqp.touristicPlaces.domain.TouristicPlaceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
): ViewModel() {


    suspend fun getTouristicPlaces(){

    }
}