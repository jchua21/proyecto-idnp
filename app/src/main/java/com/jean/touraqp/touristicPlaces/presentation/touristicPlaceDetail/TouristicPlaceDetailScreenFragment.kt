package com.jean.touraqp.touristicPlaces.presentation.touristicPlaceDetail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import coil3.load
import coil3.size.Scale
import coil3.size.Size
import com.jean.touraqp.R
import com.jean.touraqp.databinding.FragmentTouristicPlaceDetailScreenBinding
import com.jean.touraqp.touristicPlaces.presentation.touristicPlaceDetail.review.ReviewTouristicPlaceAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TouristicPlaceDetailScreenFragment :
    Fragment(R.layout.fragment_touristic_place_detail_screen) {

    private val viewModel: TouristicPlaceDetailViewModel by viewModels()
    private lateinit var reviewsAdapter: ReviewTouristicPlaceAdapter

    private var binding: FragmentTouristicPlaceDetailScreenBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentTouristicPlaceDetailScreenBinding.bind(view)
        initUI()
        initObservers()
    }

    private fun initUI() {
        setReviewAdapter()
        setReviewRecyclerView()
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
                viewModel.state.collect() { state ->
                    binding?.apply {
                        progressBarTouristicPlaceDetail.isVisible = state.isLoading
                        btnReview.visibility = if(state.isLoading) View.INVISIBLE else View.VISIBLE

                        if (state.touristicPlace == null) return@collect
                        Log.d("DETAIL_SCREEN", "${state.touristicPlace}")
                        reviewsAdapter.updateList(state.touristicPlace.reviews)
                        touristicPlaceTitle.text = state.touristicPlace.name
                        touristicPlaceDescription.text = state.touristicPlace.description
                        touristicPlaceImage.load(state.touristicPlace.imageUrl) {
                            size(Size.ORIGINAL)
                        reviewsAdapter.updateList(state.touristicPlace.reviews)
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