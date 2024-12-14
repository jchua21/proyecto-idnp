package com.jean.touraqp.touristicPlaces.domain.usecases

import android.util.Log
import com.jean.touraqp.auth.domain.UserRepository
import com.jean.touraqp.core.utils.Result
import com.jean.touraqp.touristicPlaces.domain.ReviewRepository
import com.jean.touraqp.touristicPlaces.domain.model.Review
import com.jean.touraqp.touristicPlaces.domain.model.ReviewWithUser
import javax.inject.Inject

class AddReviewUseCase @Inject constructor(
    private val reviewRepository: ReviewRepository,
    private val userRepository: UserRepository,
) {
    suspend fun execute(review: Review): Result<ReviewWithUser,Exception>{
        val result = try {
            val reviewAdded = reviewRepository.addReview(review)
            val user = userRepository.getUserById(reviewAdded.userId)

            ReviewWithUser(
                id = reviewAdded.id,
                user = user,
                rating = reviewAdded.rating,
                comment = reviewAdded.comment,
                touristicPlaceId = reviewAdded.touristicPlaceId
            )
        }catch (e: Exception){
            Log.d("ERROR", "${e.message}")
            return Result.Error(e);
        }
        return Result.Success(result)
    }
}