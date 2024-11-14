package com.jean.touraqp.touristicPlaces.presentation.touristicPlaceDetail.review

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jean.touraqp.R
import com.jean.touraqp.databinding.ItemPlaceReviewBinding
import com.jean.touraqp.touristicPlaces.domain.model.Review

class ReviewTouristicPlaceAdapter(
    private var reviewsTouristicPlace: List<Review> = emptyList()
) :
    RecyclerView.Adapter<ReviewTouristicPlaceAdapter.ReviewTouristicPlaceViewHolder>() {

    fun updateList(reviews: List<Review>){
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

        fun bind(review: Review){
            binding.apply {
                reviewUsername.text = review.username
                reviewComment.text = review.comment
                reviewRating.rating = review.rate.toFloat()
            }
        }
    }
}