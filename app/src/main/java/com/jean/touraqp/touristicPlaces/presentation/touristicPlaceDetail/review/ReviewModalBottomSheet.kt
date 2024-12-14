package com.jean.touraqp.touristicPlaces.presentation.touristicPlaceDetail.review

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.jean.touraqp.R
import com.jean.touraqp.core.auth.AuthViewModel
import com.jean.touraqp.databinding.FragmentModalReviewBottomSheetBinding
import com.jean.touraqp.touristicPlaces.domain.model.Review
import com.jean.touraqp.touristicPlaces.presentation.shared.SharedViewModel
import com.jean.touraqp.touristicPlaces.presentation.shared.TouristicPlaceEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ReviewModalBottomSheet :
    BottomSheetDialogFragment(R.layout.fragment_modal_review_bottom_sheet) {

    private var binding: FragmentModalReviewBottomSheetBinding? = null
    private val authViewModel: AuthViewModel by activityViewModels();
    private val sharedViewModel: SharedViewModel by hiltNavGraphViewModels(R.id.core_graph)
    private val reviewViewModel: ReviewViewModel by viewModels()

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
        initObservers()
    }

    private fun initObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                reviewViewModel.effect.collect() {
                    when (it) {
                        is ReviewModalEffect.OnReviewAdded -> {
                            sharedViewModel.onEvent(TouristicPlaceEvent.OnReviewAdded(it.review))
                            dismiss()
                            Toast.makeText(
                                activity,
                                "Review added successfully",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }
    }

    private fun initUI() {
        binding?.apply {
            val bottomSheet = standardBottomSheet
            bottomSheet.let {
                val behavior = BottomSheetBehavior.from(it)
                behavior.state = BottomSheetBehavior.STATE_EXPANDED
            }

            btnAddReview.setOnClickListener() {
                reviewViewModel.onEvent(
                    ReviewModalEvent.OnUserAddReview(
                        authViewModel.state.value.id!!,
                        sharedViewModel.state.value.selectedTouristicPlace!!.id
                    )
                )
            }
            reviewRating.setOnRatingBarChangeListener { _, rating, _ ->
                reviewViewModel.onEvent(ReviewModalEvent.OnUserSelectedRating(rating.toDouble()))
            }
            editTextComment.doOnTextChanged { text, _, _, _ ->
                reviewViewModel.onEvent(ReviewModalEvent.OnUserCommented(text.toString()))
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}