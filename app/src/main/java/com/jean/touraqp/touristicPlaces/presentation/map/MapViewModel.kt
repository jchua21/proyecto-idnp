package com.jean.touraqp.touristicPlaces.presentation.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jean.touraqp.core.utils.ResourceResult
import com.jean.touraqp.touristicPlaces.domain.TouristicPlaceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val touristicPlaceRepository: TouristicPlaceRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(MapUIState())
    val state = _state.asStateFlow()

    init {
        getTouristicPlaces()
    }

    private fun getTouristicPlaces() {
        viewModelScope.launch(Dispatchers.IO) {
            touristicPlaceRepository.getAllTouristicPlaces(fetchFromNetwork = false)
                .collect() { response ->
                    when (response) {
                        is ResourceResult.Error -> {
                            _state.value = _state.value.copy(hasError = true, isLoading = false)
                        }

                        is ResourceResult.Loading -> {
                            _state.value = _state.value.copy(isLoading = true)
                        }

                        is ResourceResult.Success -> {
                            _state.value = _state.value.copy(
                                touristicPlaces = response.data!!,
                                isLoading = false
                            )
                        }
                    }

                }
        }
    }
}