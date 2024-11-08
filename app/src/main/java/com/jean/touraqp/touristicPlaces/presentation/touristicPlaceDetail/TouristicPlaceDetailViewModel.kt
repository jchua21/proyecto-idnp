package com.jean.touraqp.touristicPlaces.presentation.touristicPlaceDetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jean.touraqp.core.utils.ResourceResult
import com.jean.touraqp.touristicPlaces.domain.TouristicPlaceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TouristicPlaceDetailViewModel @Inject constructor(
    private val touristicPlaceRepository: TouristicPlaceRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var args = TouristicPlaceDetailScreenFragmentArgs.fromSavedStateHandle(savedStateHandle)
    private val _state = MutableStateFlow(TouristicPlaceDetailUIState())
    val state = _state.asSharedFlow()

    init {
        getTouristicPlaceDetail()
    }

    private fun getTouristicPlaceDetail() {
        viewModelScope.launch(Dispatchers.IO) {
            touristicPlaceRepository.getTouristicPlaceDetail(
                args.touristicPlaceId
            ).collect(){result ->
                when(result){
                    is ResourceResult.Error -> {
                        _state.value = TouristicPlaceDetailUIState(hasError = true)
                    }
                    is ResourceResult.Loading -> {
                        _state.value = TouristicPlaceDetailUIState(isLoading = true)
                    }
                    is ResourceResult.Success -> {
                        _state.value = TouristicPlaceDetailUIState(touristicPlace = result.data)
                    }
                }
            }
        }
    }
}