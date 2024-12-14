package com.jean.touraqp.touristicPlaces.presentation.touristicPlaceDetail.review

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jean.touraqp.core.utils.onError
import com.jean.touraqp.core.utils.onSuccess
import com.jean.touraqp.touristicPlaces.domain.ReviewRepository
import com.jean.touraqp.touristicPlaces.domain.model.Review
import com.jean.touraqp.touristicPlaces.domain.usecases.AddReviewUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.log

@HiltViewModel
class ReviewViewModel @Inject constructor(
    private val addReviewUseCase: AddReviewUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ReviewUI())
    val state = _state.asStateFlow()

    private val _effect = Channel<ReviewModalEffect>()
    val effect = _effect.receiveAsFlow()

    companion object {
        const val TAG = "review_vm"
    }

    fun onEvent(e: ReviewModalEvent) {
        when (e) {
            is ReviewModalEvent.OnUserAddReview -> onUserAddReview(e.userId, e.touristicPlaceId)
            is ReviewModalEvent.OnUserCommented -> onUserCommented(e.comment)
            is ReviewModalEvent.OnUserSelectedRating -> onUserSelectedRating(e.rating)
        }
    }

    private fun onUserAddReview(userId: String, touristicPlaceId: String) {
        //Create Review
        val reviewUI = ReviewUI(
            userId = userId,
            touristicPlaceId = touristicPlaceId,
            comment = state.value.comment,
            rating = state.value.rating
        )

        viewModelScope.launch {
            addReviewUseCase.execute(reviewUI.toReview())
                .onSuccess { review ->
                    _effect.send(ReviewModalEffect.OnReviewAdded(review))
                }.onError {error ->
                    Log.d(TAG, "Error: ${error.message}")
                }
        }
    }

    private fun onUserCommented(comment: String) {
        _state.update {
            it.copy(
                comment = comment
            )
        }
    }

    private fun onUserSelectedRating(rating: Double) {
        _state.update {
            it.copy(
                rating = rating
            )
        }
    }

}