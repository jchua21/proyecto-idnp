package com.jean.touraqp.touristicPlaces.presentation.map

import android.Manifest
import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.jean.touraqp.R
import com.jean.touraqp.touristicPlaces.domain.model.TouristicPlace
import com.jean.touraqp.touristicPlaces.domain.model.TouristicPlaceWithReviews
import com.jean.touraqp.touristicPlaces.presentation.model.TouristicPlaceWithReviewsUI
import com.jean.touraqp.touristicPlaces.presentation.shared.SharedViewModel
import com.jean.touraqp.touristicPlaces.presentation.shared.TouristicPlaceEffect
import com.jean.touraqp.touristicPlaces.presentation.shared.TouristicPlaceEvent
import com.jean.touraqp.touristicPlaces.utils.MapLocation
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MapScreenFragment : Fragment(R.layout.fragment_map_screen), OnMapReadyCallback {
    private val sharedViewModel: SharedViewModel by hiltNavGraphViewModels(R.id.core_graph)
    private lateinit var map: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    @Inject
    lateinit var mapLocation: MapLocation

    companion object {
        const val TAG = "map_screen"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initMap()
    }

    override fun onStart() {
        Log.d(TAG, "onStart!!!")
        launchLocationPermission()
        super.onStart()
    }

    override fun onStop() {
        Log.d(TAG, "onStop")
        sharedViewModel.onEvent(TouristicPlaceEvent.OnLocationPermissionResult())
        super.onStop()
    }

    private fun initMap() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        initObservers()
    }

    private fun initObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {

                launch {
                    sharedViewModel.state.collect() { state ->
                        if (!::map.isInitialized) return@collect
                        if (state.touristicPlaces.isNotEmpty()) {

                            addMarkers(state.touristicPlaces)
                            onInfoWindowClickListener()
                        }
                        if (state.isLocationPermissionGranted != null) {
                            Log.d(TAG, "initObservers: ${state.isLocationPermissionGranted}")
                            if (!state.isLocationPermissionGranted) {
                                val isPermanentlyDisabled = !shouldShowRequestPermissionRationale(
                                    Manifest.permission.ACCESS_FINE_LOCATION
                                )
                                showPermissionDialog(
                                    isPermanentlyDisabled = isPermanentlyDisabled,
                                    title = "Permiso Requerido",
                                    message = getLocationPermissionDialogMessage(
                                        isPermanentlyDisabled
                                    ),
                                    onPositiveButton = {
                                        if (isPermanentlyDisabled) {
                                            goToSettings()
                                        } else {
                                            sharedViewModel.onEvent(TouristicPlaceEvent.OnLocationPermissionResult())
                                            launchLocationPermission()
                                        }
                                    }
                                )
                            } else {
                                map.isMyLocationEnabled = true
                                val location = mapLocation.getCurrentUserLocation()
                                if (location != null) {
                                    if (!mapLocation.isInArequipa(location)) {
                                        AlertDialog.Builder(requireContext())
                                            .setTitle("Esperamos verte de nuevo!")
                                            .setMessage("Parece que no estas en Arequipa. Visitanos pronto para explorar los lugares mas hermosos!")
                                            .setPositiveButton("OK") { dialog, _ ->
                                                dialog.dismiss()
                                            }
                                            .show()
                                    }
                                    map.moveCamera(
                                        CameraUpdateFactory.newLatLngZoom(
                                            location,
                                            14f
                                        )
                                    )
                                }
                            }

                        }
                    }
                }
                launch {
                    sharedViewModel.effect.collect() { effect ->
                        when (effect) {
                            TouristicPlaceEffect.OnSelectTouristicPlace -> {
                                navigateToDetailScreen()
                            }
                        }
                    }
                }
            }
        }
    }

    private fun getLocationPermissionDialogMessage(isPermanentlyDisabled: Boolean): String {
        return if (isPermanentlyDisabled) {
            "Parece que has rechazado el permiso de locacion. Puedes ir a la configuracion de la applicaion para concederlo"
        } else {
            "El permiso de locacion es requerido para poder mostrarte la ubicacion de los lugares turisticos."
        }
    }

    private fun showPermissionDialog(
        title: String,
        message: String,
        onPositiveButton: (dialog: DialogInterface?) -> Unit,
        isPermanentlyDisabled: Boolean
    ) {
        val positiveBtnMessage = if (isPermanentlyDisabled) "Granted" else "OK"

        AlertDialog.Builder(requireContext())
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(positiveBtnMessage) { dialog, _ -> onPositiveButton(dialog) }
            .show()
    }

    private fun goToSettings() {
        //Go to settings
        Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.fromParts("package", requireContext().packageName, null)
        ).also {
            startActivity(it)
        }
    }

    @SuppressLint("MissingPermission")
    private val locationPermissionResultLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            sharedViewModel.onEvent(TouristicPlaceEvent.OnLocationPermissionResult(granted))
        }


    private fun launchLocationPermission() {
        locationPermissionResultLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }

    private fun addMarkers(touristicPlaces: List<TouristicPlaceWithReviewsUI>) {
        if (::map.isInitialized) {
            touristicPlaces.forEach { touristicPlace ->
                val markerLocation = LatLng(touristicPlace.latitude, touristicPlace.longitude)
                //Set a tag to retrieve data
                val markerAdded = map.addMarker(
                    MarkerOptions().position(markerLocation).title(touristicPlace.name)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_place_img))
                )
                markerAdded?.tag = touristicPlace.id
            }
        }
    }

    private fun onInfoWindowClickListener() {
        map.setOnInfoWindowClickListener { marker ->
            val touristicPlaceId = marker.tag as? String ?: return@setOnInfoWindowClickListener
            sharedViewModel.onEvent(TouristicPlaceEvent.OnSelectTouristicPlace(touristicPlaceId))
        }
    }

    private fun navigateToDetailScreen() {
        findNavController().navigate(
            MapScreenFragmentDirections.actionMapScreenFragmentToTouristicPlaceDetailScreenFragment()
        )
    }
}