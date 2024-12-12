package com.jean.touraqp.touristicPlaces.data.repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.jean.touraqp.auth.data.remote.dto.UserDto
import com.jean.touraqp.core.TourAqpDB
import com.jean.touraqp.core.constants.DBCollection
import com.jean.touraqp.core.utils.ResourceResult
import com.jean.touraqp.core.utils.Result
import com.jean.touraqp.core.utils.toObjectWithId
import com.jean.touraqp.touristicPlaces.data.mapper.toReview
import com.jean.touraqp.touristicPlaces.data.mapper.toTouristicPlace
import com.jean.touraqp.touristicPlaces.data.remote.dto.ReviewDto
import com.jean.touraqp.touristicPlaces.data.remote.dto.TouristicPlaceDto
import com.jean.touraqp.touristicPlaces.domain.TouristicPlaceRepository
import com.jean.touraqp.touristicPlaces.domain.model.Review
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
        const val TAG = "touristic_place_repository"
    }

    private val touristicPlacesCollection = remoteDB.collection(DBCollection.TOURISTIC_PLACE)
    private val reviewsCollection = remoteDB.collection(DBCollection.REVIEW)
    private val usersCollection = remoteDB.collection(DBCollection.USER)

    private val touristicPlaceDao = localDB.getTouristicPlaceDao()

    override suspend fun getAllTouristicPlaces(fetchFromNetwork: Boolean): Result<List<TouristicPlace>, Exception> {
        try {
            val touristicPlaces = if (fetchFromNetwork) {
                //Get from network
                val touristicPlacesDto = getTouristicPlacesFromNetwork()

                val touristicPlacesWithReviewsWithUser =
                    touristicPlacesDto.map() { touristicPlace ->
                        //Fetch reviews for each touristicPlace
                        val reviews = getReviewsFromTouristicPlace(touristicPlace)

                        // TODO Store in Room

                        return@map touristicPlace.toTouristicPlace(touristicPlace.id!!, reviews)
                    }
                touristicPlacesWithReviewsWithUser
            } else {
                // Get from ROOM
                val touristicPlacesEntities = touristicPlaceDao.getAllTouristicPlaces()
                val touristicPlaces = touristicPlacesEntities.map { touristicPlaceEntity ->
                    touristicPlaceEntity.toTouristicPlace()
                }
                touristicPlaces
            }

            Log.d(TAG, "$touristicPlaces")
            //Send results
            return Result.Success(touristicPlaces)

        } catch (e: Exception) {
            Log.d(TAG, "${e.message}")
            return Result.Error(e);
        }
    }
    private suspend fun  getTouristicPlacesFromNetwork(): List<TouristicPlaceDto>{
        val touristicPlacesResult = touristicPlacesCollection.limit(3).get().await().documents
        return touristicPlacesResult.map {
            it.toObjectWithId<TouristicPlaceDto>()
        }
    }

    private suspend fun getReviewsFromTouristicPlace(touristicPlaceDto: TouristicPlaceDto): List<Review>{
        //Fetch reviews for each touristicPlace
        val reviewsResult =
            reviewsCollection.whereEqualTo("touristicPlaceId", touristicPlaceDto.id)
                .limit(3).get()
                .await().documents

        val reviewsDto = reviewsResult.map { review ->
            review.toObjectWithId<ReviewDto>()
        }
        val reviews = reviewsDto.map { reviewDto ->
            val userResult =
                usersCollection.document(reviewDto.userId).get().await()
            val userDto =
                userResult.toObject<UserDto>() ?: throw Exception("Data Mismatch")
            reviewDto.toReview(userDto)
        }

        return reviews
    }
}