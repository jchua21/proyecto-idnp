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
import com.jean.touraqp.touristicPlaces.presentation.touristicPlaceDetail.review.ReviewUI
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
                    _state.update {
                        it.copy(isLoading = false)
                    }
                    _state.update {
                        it.copy(
                            touristicPlaces = result.toPresentation()
                        )
                    }
                }
                .onError {
                    _state.update {
                        it.copy(isLoading = false)
                    }
                    _state.value = TouristicPlaceState(hasError = true)
                }
        }
    }
}