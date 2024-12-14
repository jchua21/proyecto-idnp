package com.jean.touraqp.touristicPlaces.presentation.touristicPlaceDetail.review

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil3.load
import coil3.request.transformations
import coil3.size.Size
import coil3.transform.CircleCropTransformation
import com.jean.touraqp.R
import com.jean.touraqp.databinding.ItemPlaceReviewBinding
import com.jean.touraqp.touristicPlaces.domain.model.Review
import com.jean.touraqp.touristicPlaces.domain.model.ReviewWithUser

class ReviewTouristicPlaceAdapter(
    private var reviewsTouristicPlace: List<ReviewWithUser> = emptyList()
) :
    RecyclerView.Adapter<ReviewTouristicPlaceAdapter.ReviewTouristicPlaceViewHolder>() {

    fun updateList(reviews: List<ReviewWithUser>){
        this.reviewsTouristicPlace = reviews
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ReviewTouristicPlaceViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_place_review, parent, false)
        return ReviewTouristicPlaceViewHolder(view)
    }

    override fun getItemCount(): Int {
       return  reviewsTouristicPlace.size
    }

    override fun onBindViewHolder(holder: ReviewTouristicPlaceViewHolder, position: Int) {
        holder.bind(reviewsTouristicPlace[position])
    }


    inner class ReviewTouristicPlaceViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val binding : ItemPlaceReviewBinding = ItemPlaceReviewBinding.bind(view)

        fun bind(review: ReviewWithUser){
            binding.apply {
                reviewUsername.text = review.user.username
                reviewComment.text = review.comment
                reviewRating.rating = review.rating.toFloat()
                reviewUserImage.load(review.user.imageUrl){
                    size(Size.ORIGINAL)
                    transformations(CircleCropTransformation())
                }
            }
        }
    }
}