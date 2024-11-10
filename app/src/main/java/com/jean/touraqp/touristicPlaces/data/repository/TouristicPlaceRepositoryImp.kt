package com.jean.touraqp.touristicPlaces.data.repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.google.firebase.firestore.toObjects
import com.jean.touraqp.core.TourAqpDB
import com.jean.touraqp.core.constants.DBCollection
import com.jean.touraqp.core.seed.TouristicPlacesSeed
import com.jean.touraqp.core.utils.ResourceResult
import com.jean.touraqp.touristicPlaces.data.local.entities.TouristicPlaceEntity
import com.jean.touraqp.touristicPlaces.data.mapper.toTouristicPlace
import com.jean.touraqp.touristicPlaces.data.mapper.toTouristicPlaceEntity
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
    private val remoteDB: FirebaseFirestore,
    private val localDB: TourAqpDB

) : TouristicPlaceRepository {

    companion object {
        const val TAG = "touristic_place"
    }

    private val touristicPlacesCollection = remoteDB.collection(DBCollection.TOURISTIC_PLACE)
    private val touristicPlaceDao = localDB.getTouristicPlaceDao()

    override suspend fun getAllTouristicPlaces(): Flow<ResourceResult<List<TouristicPlace>>> =
        flow {
            try {
                Log.d(TAG, "CALLING ")
                emit(ResourceResult.Loading())

                //Get from network
                val result = touristicPlacesCollection.limit(3).get().await()
                val places = result.documents
                //Convert to Room Entity
                val touristicPlacesEntities = places.map { touristicPlace ->
                    val touristicPlaceDto = touristicPlace.toObject<TouristicPlaceDto>()
                    touristicPlaceDto?.toTouristicPlaceEntity(touristicPlace.id) ?: throw Exception(
                        "Data Mismatch"
                    )
                }
                // Add to Room
                touristicPlaceDao.insertAll(touristicPlacesEntities)

                val touristicPlaces = touristicPlacesEntities.map { touristicPlaceEntity ->
                    touristicPlaceEntity.toTouristicPlace()
                }

                //Send results
                emit(
                    ResourceResult.Success(
                        data = touristicPlaces,
                        message = "Successful Query"
                    )
                )
            } catch (e: Exception) {
                Log.d(TAG, "${e.message}")
                emit(
                    ResourceResult.Success(
                        message = "Something went wrong"
                    )
                )
            }
        }

    override suspend fun getTouristicPlaceDetail(id: String): Flow<ResourceResult<TouristicPlace>> =
        flow {
            try {
                emit(ResourceResult.Loading(message = "Loading..."))
                //Single Source of Truth
                val result = touristicPlaceDao.getTouristicPlaceById(id)
                val place = result.toTouristicPlace()
                emit(
                    ResourceResult.Success(
                        data = place,
                        message = "Successful Query"
                    )
                )
            } catch (e: Exception) {
                Log.d(TAG, "${e.message}")
                emit(
                    ResourceResult.Success(
                        message = "Something went wrong"
                    )
                )
            }
        }
}