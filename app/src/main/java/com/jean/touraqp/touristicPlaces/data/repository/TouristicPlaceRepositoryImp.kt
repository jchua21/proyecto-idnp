package com.jean.touraqp.touristicPlaces.data.repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObjects
import com.jean.touraqp.core.constants.DBCollection
import com.jean.touraqp.core.seed.TouristicPlacesSeed
import com.jean.touraqp.core.utils.ResourceResult
import com.jean.touraqp.touristicPlaces.data.mapper.toTouristicPlace
import com.jean.touraqp.touristicPlaces.data.remote.dto.TouristicPlaceDto
import com.jean.touraqp.touristicPlaces.domain.TouristicPlaceRepository
import com.jean.touraqp.touristicPlaces.domain.model.TouristicPlace
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TouristicPlaceRepositoryImp @Inject constructor(
    db: FirebaseFirestore
) : TouristicPlaceRepository {

    companion object {
        const val TAG = "touristic_place"
    }

    private val touristicPlacesCollection = db.collection(DBCollection.TOURISTIC_PLACE)

    override suspend fun getAllTouristicPlaces(): Flow<ResourceResult<List<TouristicPlace>>> = flow{
        try {

            emit(ResourceResult.Loading(message = "Loading..."))
            val snapshotPlaces = touristicPlacesCollection.limit(3).get().await()
            val places = snapshotPlaces.toObjects<TouristicPlaceDto>()
            emit(ResourceResult.Success(
                data = places.map { it.toTouristicPlace() },
                message = "Successful Query"
            ))
        }catch (e: Exception){
            Log.d(TAG, "${e.message}")
            emit(ResourceResult.Success(
                message = "Something went wrong"
            ))
        }
    }
}