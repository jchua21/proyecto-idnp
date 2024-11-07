package com.jean.touraqp.touristicPlaces.domain

import javax.inject.Inject

class GetAllTouristicPlaces @Inject constructor(
    private val touristicPlaceRepository: TouristicPlaceRepository
){

    suspend fun execute(){
        touristicPlaceRepository.getAllTouristicPlaces()
    }
}