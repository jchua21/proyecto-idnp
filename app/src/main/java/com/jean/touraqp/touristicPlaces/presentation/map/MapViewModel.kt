package com.jean.touraqp.touristicPlaces.presentation.map

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel(){

    private val args  = MapScreenFragmentArgs.fromSavedStateHandle(savedStateHandle = savedStateHandle)

    private val _state = MutableStateFlow(MapState())
    val state = _state
        .onStart {
           val idSelected = args.idSelected

            if(idSelected != null){
                _state.update {
                    it.copy(
                        idMarkerSelected = idSelected
                    )
                }
            }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            MapState()
        )

    fun onEvent(e: MapEvent){
        when(e){
            is MapEvent.OnAddMarkers -> {
                _state.update {
                    it.copy(markers = e.markers)
                }
            }
        }
    }

}