package com.jean.touraqp.touristicPlaces.presentation.touristicPlaceDetail.review

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.jean.touraqp.R
import com.jean.touraqp.databinding.FragmentModalReviewBottomSheetBinding
import com.jean.touraqp.touristicPlaces.domain.model.Review
import com.jean.touraqp.touristicPlaces.presentation.shared.SharedViewModel
import com.jean.touraqp.touristicPlaces.presentation.shared.TouristicPlaceEvent

class ReviewModalBottomSheet :
    BottomSheetDialogFragment(R.layout.fragment_modal_review_bottom_sheet) {

    private var binding: FragmentModalReviewBottomSheetBinding? = null
    private val sharedViewModel : SharedViewModel by hiltNavGraphViewModels(R.id.core_graph)

    companion object {
        const val TAG = "review_modal"
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        Log.d(TAG, "onCreateDialog")
        return super.onCreateDialog(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentModalReviewBottomSheetBinding.bind(view)
        initUI()
    }

    private fun initUI() {
        binding?.apply {
            val bottomSheet = standardBottomSheet
            bottomSheet.let {
                val behavior = BottomSheetBehavior.from(it)
                behavior.state = BottomSheetBehavior.STATE_EXPANDED
            }

            btnAddReview.setOnClickListener() {
                Log.d(TAG, "Create Review....")
//                val review = Review(
//                    rate = reviewRating.rating.toDouble(),
//                    comment = editTextComment.text,
//
//                )
//                sharedViewModel.onEvent(TouristicPlaceEvent.OnAddReviewClick())
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}