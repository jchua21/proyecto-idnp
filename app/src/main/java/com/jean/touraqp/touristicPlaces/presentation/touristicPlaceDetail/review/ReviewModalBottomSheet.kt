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
        const val TAG = "ReviewModalBottomSheet"
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        val a = dialog.behavior
        return dialog
    }


    override fun onStart() {
        super.onStart()
        dialog?.let { dialog ->
            val bottomSheet = dialog.findViewById<View>(R.id.standard_bottom_sheet)
//            val bottomSheet = binding?.standardBottomSheet
            bottomSheet?.let {
                val behavior = BottomSheetBehavior.from(it)
                behavior.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}