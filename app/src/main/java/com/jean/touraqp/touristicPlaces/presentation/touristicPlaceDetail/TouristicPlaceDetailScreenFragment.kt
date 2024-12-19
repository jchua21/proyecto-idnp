package com.jean.touraqp.touristicPlaces.presentation.touristicPlaceDetail

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
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
        const val TAG = "detail_screen"
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            // Permission granted, you can show notifications
            showNotification()
        } else {
            // Permission denied, handle the case
            handlePermissionDenied()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentTouristicPlaceDetailScreenBinding.bind(view)
        checkNotificationPermission()
        initUI()
        initObservers()
    }

    private fun checkNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            when {
                ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED -> {
                    // Permission already granted
                    showNotification()
                }
                shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS) -> {
                    // Show an explanation to the user
                    showPermissionExplanation()
                }
                else -> {
                    // Request the permission
                    requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
            }
        }
    }

    private fun showNotification() {
        // Logic to show notification
    }

    private fun handlePermissionDenied() {
        // Logic to handle permission denied
    }

    private fun showPermissionExplanation() {
        // Logic to show permission explanation to the user
    }

    private fun initUI() {
        setReviewAdapter()
        setReviewRecyclerView()
        setOnClickListenerReviewBtn()
        setOnClickListenerTouristicPlaneBtn()
        setOnClickMapButton()
        setAudioControlButtons()
    }

    private fun setOnClickMapButton() {
        binding?.icTouristicPlaceLocation?.setOnClickListener {
            findNavController().navigate(
                TouristicPlaceDetailScreenFragmentDirections.actionTouristicPlaceDetailScreenFragmentToMapScreenFragment(
                    idSelected = sharedViewModel.state.value.selectedTouristicPlace?.id ?: ""
                )
            )
        }
    }

    private fun setOnClickListenerReviewBtn() {
        binding?.btnReview?.setOnClickListener {
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

    private fun setOnClickListenerTouristicPlaneBtn() {
        binding?.icTouristicPlacePlane?.setOnClickListener {
            findNavController().navigate(R.id.planeFragment)
        }
    }

    private fun setAudioControlButtons() {
        binding?.apply {
            icTouristicPlaceSound.setOnClickListener {
                onStartClick()
            }
            icTouristicPlacePause.setOnClickListener { onPauseClick() }
            icTouristicPlaceStop.setOnClickListener { onStopClick() }
        }
    }

    private fun onStartClick() {
        val placeName = sharedViewModel.state.value.selectedTouristicPlace?.name
        val intent = Intent(requireContext(), AudioPlayerService::class.java).apply {
            action = "START"
            putExtra("PLACE_NAME", placeName)
        }
        ContextCompat.startForegroundService(requireContext(), intent)
    }

    private fun onPauseClick() {
        val intent = Intent(requireContext(), AudioPlayerService::class.java).apply { action = "PAUSE" }
        ContextCompat.startForegroundService(requireContext(), intent)
    }

    private fun onStopClick() {
        val intent = Intent(requireContext(), AudioPlayerService::class.java).apply { action = "STOP" }
        ContextCompat.startForegroundService(requireContext(), intent)
    }

    override fun onPause() {
        super.onPause()
        val intent = Intent(requireContext(), AudioPlayerService::class.java).apply { action = "SHOW_NOTIFICATION" }
        ContextCompat.startForegroundService(requireContext(), intent)
    }

    override fun onResume() {
        super.onResume()
        val intent = Intent(requireContext(), AudioPlayerService::class.java).apply { action = "HIDE_NOTIFICATION" }
        ContextCompat.startForegroundService(requireContext(), intent)
    }

    private fun initObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    sharedViewModel.state.collect { state ->
                        binding?.apply {
                            progressBarTouristicPlaceDetail.isVisible = state.isLoading
                            reviewsAdapter.updateList(state.selectedTouristicPlace?.reviews ?: mutableListOf())
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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}