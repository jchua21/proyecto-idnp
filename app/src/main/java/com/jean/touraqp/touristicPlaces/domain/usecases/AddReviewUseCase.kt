package com.jean.touraqp.touristicPlaces.domain.usecases

import android.util.Log
import com.jean.touraqp.core.utils.Result
import com.jean.touraqp.touristicPlaces.domain.ReviewRepository
import com.jean.touraqp.touristicPlaces.domain.model.Review
import javax.inject.Inject

class AddReviewUseCase @Inject constructor(
    private val reviewRepository: ReviewRepository
) {
    suspend fun execute(review: Review): Result<Review,Exception>{
        val result = try {
            val reviewAdded = reviewRepository.addReview(review)
            reviewAdded
        }catch (e: Exception){
            Log.d("ERROR", "${e.message}")
            return Result.Error(e);
        }
        return Result.Success(result)
    }
}