package com.jean.touraqp.touristicPlaces.presentation.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jean.touraqp.core.utils.ResourceResult
import com.jean.touraqp.touristicPlaces.domain.TouristicPlaceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val touristicPlaceRepository: TouristicPlaceRepository
) : ViewModel() {

    companion object {
        const val TAG = "search_view_model"
    }

    private val _searchState = MutableStateFlow(SearchUIState())
    val searchState = _searchState.asStateFlow()

    init {
        getTouristicPlaces()
    }


    private fun getTouristicPlaces() {
        viewModelScope.launch {
            touristicPlaceRepository.getAllTouristicPlaces().collect() { result ->
                when (result) {
                    is ResourceResult.Error -> {
                        _searchState.value = SearchUIState(hasError = true)
                    }

                    is ResourceResult.Loading -> {
                        _searchState.value =
                            SearchUIState(isLoading = true)
                    }

                    is ResourceResult.Success -> {
                        _searchState.value =
                            SearchUIState(touristicPlaceList = result.data ?: emptyList())
                    }
                }
            }
        }
    }
}