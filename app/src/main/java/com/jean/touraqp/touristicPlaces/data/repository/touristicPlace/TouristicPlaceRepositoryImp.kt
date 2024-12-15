package com.jean.touraqp.touristicPlaces.data.repository.touristicPlace

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.jean.touraqp.auth.data.remote.dto.UserDto
import com.jean.touraqp.core.TourAqpDB
import com.jean.touraqp.core.constants.DBCollection
import com.jean.touraqp.core.utils.Result
import com.jean.touraqp.core.utils.toObjectWithId
import com.jean.touraqp.touristicPlaces.data.mapper.toReview
import com.jean.touraqp.touristicPlaces.data.mapper.toTouristicPlace
import com.jean.touraqp.touristicPlaces.data.remote.dto.ReviewDto
import com.jean.touraqp.touristicPlaces.data.remote.dto.TouristicPlaceDto
import com.jean.touraqp.touristicPlaces.data.repository.touristicPlace.datasource.TouristicPlaceRemoteDatasource
import com.jean.touraqp.touristicPlaces.domain.TouristicPlaceRepository
import com.jean.touraqp.touristicPlaces.domain.model.Review
import com.jean.touraqp.touristicPlaces.domain.model.TouristicPlace
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TouristicPlaceRepositoryImp @Inject constructor(
    private val remoteDatasource: TouristicPlaceRemoteDatasource

) : TouristicPlaceRepository {

    companion object {
        const val TAG = "touristic_place_repository"
    }

    override suspend fun getTouristicPlaces(
        fetchFromNetwork: Boolean,
        query: String
    ): List<TouristicPlace> {
        val touristicPlaces = remoteDatasource.getTouristicPlaces(query)
        return touristicPlaces
    }
}