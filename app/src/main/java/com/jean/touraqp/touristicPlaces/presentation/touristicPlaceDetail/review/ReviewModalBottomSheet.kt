package com.jean.touraqp.touristicPlaces.presentation.touristicPlaceDetail.review

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.jean.touraqp.R
import com.jean.touraqp.databinding.FragmentModalReviewBottomSheetBinding

class ReviewModalBottomSheet : BottomSheetDialogFragment(R.layout.fragment_modal_review_bottom_sheet){

    private var binding : FragmentModalReviewBottomSheetBinding? = null

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
        val bottomSheet = binding?.standardBottomSheet
        Log.d(TAG, "binding: ${bottomSheet?.id}")

        bottomSheet?.let {
            val behavior = BottomSheetBehavior.from(it)
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}