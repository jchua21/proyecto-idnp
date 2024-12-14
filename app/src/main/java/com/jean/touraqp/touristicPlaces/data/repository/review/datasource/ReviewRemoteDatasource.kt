package com.jean.touraqp.touristicPlaces.data.repository.review.datasource

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.jean.touraqp.auth.data.remote.dto.UserDto
import com.jean.touraqp.core.constants.DBCollection
import com.jean.touraqp.core.utils.Result
import com.jean.touraqp.core.utils.toObjectWithId
import com.jean.touraqp.touristicPlaces.data.mapper.toReview
import com.jean.touraqp.touristicPlaces.data.mapper.toReviewDTO
import com.jean.touraqp.touristicPlaces.data.mapper.toReviewInsertDTO
import com.jean.touraqp.touristicPlaces.data.remote.dto.ReviewDto
import com.jean.touraqp.touristicPlaces.domain.model.Review
import com.jean.touraqp.touristicPlaces.domain.model.ReviewWithUser
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ReviewRemoteDatasource @Inject constructor(
    private val db: FirebaseFirestore
) {
    private val reviewCollection = db.collection(DBCollection.REVIEW)

    companion object {
        const val TAG = "review_remote"
    }

    suspend fun addReview(review: Review): Review {
        val reviewDto = review.toReviewInsertDTO()
        val reviewReference = reviewCollection.add(reviewDto).await()
        val reviewAdded = reviewReference.get().await().toObjectWithId<ReviewDto>()
        return reviewAdded.toReview()
    }

    suspend fun getByTouristicPlaceId(touristicPlaceId: String): List<Review> {
        val reviewsResult =
            reviewCollection.whereEqualTo("touristicPlaceId", touristicPlaceId)
                .limit(3).get()
                .await().documents
        val reviewsDto = reviewsResult.map { review ->
            review.toObjectWithId<ReviewDto>()
        }
        val reviews = reviewsDto.map { reviewDto ->
            reviewDto.toReview()
        }
        return reviews
    }
}