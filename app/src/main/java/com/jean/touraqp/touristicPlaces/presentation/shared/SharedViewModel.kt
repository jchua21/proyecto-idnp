package com.jean.touraqp.touristicPlaces.presentation.shared

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jean.touraqp.auth.presentation.model.UserUI
import com.jean.touraqp.core.utils.ResourceResult
import com.jean.touraqp.core.utils.onError
import com.jean.touraqp.core.utils.onSuccess
import com.jean.touraqp.touristicPlaces.domain.TouristicPlaceRepository
import com.jean.touraqp.touristicPlaces.domain.model.Review
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val touristicPlaceRepository: TouristicPlaceRepository,
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

            is TouristicPlaceEvent.OnAddReviewClick -> {
                onButtonReviewClick(e.review)
            }
        }
    }

    private fun onButtonReviewClick(review: Review) {
        viewModelScope.launch(Dispatchers.IO) {
//            touristicPlaceRepository.addReview(review)
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
            touristicPlaceRepository.getAllTouristicPlaces()
                .onSuccess { result ->
                    _state.update {
                        it.copy(
                            touristicPlaces = result
                        )
                    }
                }
                .onError {
                    _state.value = TouristicPlaceState(hasError = true)
                }
        }
    }
}