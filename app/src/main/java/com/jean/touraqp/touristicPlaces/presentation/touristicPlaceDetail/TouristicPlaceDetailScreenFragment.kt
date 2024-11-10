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
import coil3.load
import coil3.size.Scale
import coil3.size.Size
import com.jean.touraqp.R
import com.jean.touraqp.databinding.FragmentTouristicPlaceDetailScreenBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TouristicPlaceDetailScreenFragment :
    Fragment(R.layout.fragment_touristic_place_detail_screen) {

    private val viewModel: TouristicPlaceDetailViewModel by viewModels()

    private var binding: FragmentTouristicPlaceDetailScreenBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentTouristicPlaceDetailScreenBinding.bind(view)
        initObservers()
    }

    private fun initObservers() {

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect() { state ->
                    binding?.apply {
                        progressBarTouristicPlaceDetail.isVisible = state.isLoading

                        if (state.touristicPlace == null) return@collect
                        touristicPlaceTitle.text = state.touristicPlace.name
                        touristicPlaceDescription.text = state.touristicPlace.description
                        touristicPlaceImage.load(state.touristicPlace.imageUrl) {
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