package com.jean.touraqp.touristicPlaces.presentation.shared

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jean.touraqp.core.utils.onError
import com.jean.touraqp.core.utils.onSuccess
import com.jean.touraqp.touristicPlaces.domain.model.ReviewWithUser
import com.jean.touraqp.touristicPlaces.domain.usecases.GetTouristicPlacesWithReviews
import com.jean.touraqp.touristicPlaces.presentation.mapper.toPresentation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val getTouristicPlacesWithReviews: GetTouristicPlacesWithReviews,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    companion object {
        const val TAG = "shared_view_model"
    }

    init {
        getTouristicPlaces()
    }

    private val _state = MutableStateFlow(TouristicPlaceState())
    val state = _state.asStateFlow()

    private val _effect = Channel<TouristicPlaceEffect>()
    val effect = _effect.receiveAsFlow()

    fun onEvent(e: TouristicPlaceEvent) {
        when (e) {
            is TouristicPlaceEvent.OnSelectTouristicPlace -> {
                onSelectTouristicPlace(e.id)
            }

            is TouristicPlaceEvent.OnLocationPermissionResult -> {
                onLocationPermissionResult(e.granted)
            }

            is TouristicPlaceEvent.OnReviewAdded -> {
                onReviewAdded(e.review)
            }

            is TouristicPlaceEvent.OnSearchInputChanged -> {
                onSearchInputChanged(e.query)
            }

            TouristicPlaceEvent.OnSearchAction -> {
                onSearchAction()
            }
        }
    }

    private fun onSearchAction(){
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true
                )
            }

            val filteredResults = if (state.value.searchQuery.isEmpty()) {
                state.value.touristicPlaces
            } else {
                state.value.touristicPlaces.filter {
                    it.name.contains(state.value.searchQuery, ignoreCase = true)
                }
            }

            _state.update {
                it.copy(
                    filteredTouristicPlaces = filteredResults,
                    isLoading = false
                )
            }

        }
    }

    private fun onSearchInputChanged(query: String){
        _state.update {
            it.copy(
                searchQuery = query
            )
        }
    }

    private fun onReviewAdded(review: ReviewWithUser) {
        _state.update {
            val updatedTouristicPlace = it.selectedTouristicPlace?.copy(
                reviews = it.selectedTouristicPlace.reviews.toMutableList().apply {
                    add(review)
                }
            )
            val updatedTouristicPlaces = it.touristicPlaces.map { touristicPlace ->
                if (touristicPlace.id == updatedTouristicPlace?.id) {
                    updatedTouristicPlace
                } else {
                    touristicPlace
                }
            }
            it.copy(
                selectedTouristicPlace = updatedTouristicPlace,
                touristicPlaces = updatedTouristicPlaces
            )
        }
    }

    private fun onLocationPermissionResult(granted: Boolean? = null) {
        _state.update { it.copy(isLocationPermissionGranted = granted) }
    }

    private fun onSelectTouristicPlace(id: String) {
        viewModelScope.launch {
            val selectedTouristicPlace = _state.value.touristicPlaces.find {
                it.id == id
            }
            _state.update {
                it.copy(selectedTouristicPlace = selectedTouristicPlace)
            }
            _effect.send(TouristicPlaceEffect.OnSelectTouristicPlace)
        }
    }

    private fun getTouristicPlaces() {
        viewModelScope.launch {
            _state.update {
                it.copy(isLoading = true)
            }
            getTouristicPlacesWithReviews.execute()
                .onSuccess { result ->
                    val resultUI = result.toPresentation()

                    _state.update {
                        it.copy(
                            touristicPlaces = resultUI,
                            filteredTouristicPlaces = resultUI
                        )
                    }
                }
                .onError {error ->
                    _state.value = TouristicPlaceState(hasError = true)
                }

            _state.update {
                it.copy(isLoading = false)
            }
        }
    }
}