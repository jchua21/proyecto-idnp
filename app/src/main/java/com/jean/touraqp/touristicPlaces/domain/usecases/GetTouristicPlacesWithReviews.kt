package com.jean.touraqp.touristicPlaces.domain.usecases

import android.util.Log
import com.jean.touraqp.auth.domain.UserRepository
import com.jean.touraqp.core.utils.Result
import com.jean.touraqp.touristicPlaces.domain.ReviewRepository
import com.jean.touraqp.touristicPlaces.domain.TouristicPlaceRepository
import com.jean.touraqp.touristicPlaces.domain.model.ReviewWithUser
import com.jean.touraqp.touristicPlaces.domain.model.TouristicPlaceWithReviews
import javax.inject.Inject

class GetTouristicPlacesWithReviews @Inject constructor(
    private val reviewRepository: ReviewRepository,
    private val touristicPlaceRepository: TouristicPlaceRepository,
    private val userRepository: UserRepository
) {
    suspend fun execute(): Result<List<TouristicPlaceWithReviews>, Exception> {
        val result = try {
            val touristicPlaces = touristicPlaceRepository.getAllTouristicPlaces()
            val reviews = touristicPlaces.map { touristicPlace ->
                val reviewsByTouristicPlace =
                    reviewRepository.getByTouristicPlaceId(touristicPlace.id)
                val reviewsWithUser = reviewsByTouristicPlace.map { review ->
                    val user = userRepository.getUserById(review.userId)
                    val reviewWithUser = ReviewWithUser(
                        id = review.id,
                        comment = review.comment,
                        rating = review.rating,
                        user = user,
                        touristicPlaceId = review.touristicPlaceId,
                    )
                    reviewWithUser
                }
                reviewsWithUser
            }

            val touristicPlacesWithReviews: List<TouristicPlaceWithReviews> =
                touristicPlaces.mapIndexed() { index, touristicPlace ->
                    TouristicPlaceWithReviews(
                        id = touristicPlace.id,
                        name = touristicPlace.name,
                        description = touristicPlace.description,
                        imageUrl = touristicPlace.imageUrl,
                        latitude = touristicPlace.latitude,
                        longitude = touristicPlace.longitude,
                        reviews = reviews[index]
                    )
                }
            touristicPlacesWithReviews
        } catch (e: Exception) {
            Log.d("ERROR", "${e.message}")
            return Result.Error(e)
        }
        return Result.Success(result)
    }
}
