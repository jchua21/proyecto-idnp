package com.jean.touraqp.touristicPlaces.presentation.touristicPlaceDetail

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import coil3.load
import coil3.size.Size
import com.jean.touraqp.R
import com.jean.touraqp.databinding.FragmentTouristicPlaceDetailScreenBinding
import com.jean.touraqp.touristicPlaces.presentation.shared.SharedViewModel
import com.jean.touraqp.touristicPlaces.presentation.touristicPlaceDetail.review.ReviewModalBottomSheet
import com.jean.touraqp.touristicPlaces.presentation.touristicPlaceDetail.review.ReviewTouristicPlaceAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TouristicPlaceDetailScreenFragment :
    Fragment(R.layout.fragment_touristic_place_detail_screen) {

    private val sharedViewModel: SharedViewModel by hiltNavGraphViewModels(R.id.core_graph)
    private lateinit var reviewsAdapter: ReviewTouristicPlaceAdapter

    private var binding: FragmentTouristicPlaceDetailScreenBinding? = null

    companion object {
        const val  TAG = "detail_screen"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentTouristicPlaceDetailScreenBinding.bind(view)
        initUI()
        initObservers()
    }

    private fun initUI() {
        setReviewAdapter()
        setReviewRecyclerView()
        setOnClickListenerReviewBtn()
    }

    private fun setOnClickListenerReviewBtn() {
        binding?.btnReview?.setOnClickListener() {
            val bottomSheetDialogFragment = ReviewModalBottomSheet()
            bottomSheetDialogFragment.show(parentFragmentManager, ReviewModalBottomSheet.TAG)
        }
    }

    private fun setReviewAdapter() {
        reviewsAdapter = ReviewTouristicPlaceAdapter()
    }

    private fun setReviewRecyclerView() {
        binding?.rvReviews?.apply {
            setHasFixedSize(true)
            adapter = reviewsAdapter
            layoutManager = LinearLayoutManager(this@TouristicPlaceDetailScreenFragment.context)
        }
    }

    private fun initObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                sharedViewModel.state.collect() { state ->
                    binding?.apply {
                        progressBarTouristicPlaceDetail.isVisible = state.isLoading
                        reviewsAdapter.updateList(state.selectedTouristicPlace?.reviews.orEmpty())
                        touristicPlaceTitle.text = state.selectedTouristicPlace?.name
                        touristicPlaceDescription.text = state.selectedTouristicPlace?.description
                        touristicPlaceImage.load(state.selectedTouristicPlace?.imageUrl) {
                            size(Size.ORIGINAL)
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}