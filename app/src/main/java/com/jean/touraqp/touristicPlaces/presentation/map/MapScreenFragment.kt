package com.jean.touraqp.touristicPlaces.presentation.map

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
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
import com.jean.touraqp.touristicPlaces.utils.MapLocation
import com.jean.touraqp.touristicPlaces.domain.model.TouristicPlace
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MapScreenFragment : Fragment(R.layout.fragment_map_screen), OnMapReadyCallback {
    private val viewModel: MapViewModel by viewModels()
    private lateinit var map: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    @Inject
    lateinit var mapLocation: MapLocation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initMap()
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
                handleLocation()
                viewModel.state.collect() { state ->
                    if (!::map.isInitialized) return@collect

                    if (state.touristicPlaces.isNotEmpty()) {
                        addMarkers(state.touristicPlaces)
                    }
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    private suspend fun handleLocation() {

        if (mapLocation.isLocationPermissionGranted()) {
            map.isMyLocationEnabled = true
            val location = mapLocation.getCurrentUserLocation()

            if (location != null) {
                if (!mapLocation.isInArequipa(location)) {
                    showDialog(
                        title = "We Hope to See You Again!",
                        message = "It looks like you're not in Arequipa. Visit us soon to explore the beautiful places!"
                    )
                }
                map.moveCamera(
                    CameraUpdateFactory.newLatLngZoom(
                        location,
                        14f
                    )
                )
            } else {
                showDialog(
                    title = "Enable GPS",
                    message = "No se puede obtener la ubicación. Por favor, activa el GPS."
                )
            }

        } else if (mapLocation.showShowRationale()) {
            showDialog(
                title = "Permisos",
                message = "El permiso de ubicación es necesario para mostrar las ubicaciones de los lugares turísticos en el mapa"
            )

        } else {
            requestPermissionLauncher.launch(
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        }


    }

    private fun showDialog(title: String, message: String) {
        AlertDialog.Builder(requireContext())
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun addMarkers(touristicPlaces: List<TouristicPlace>) {
        if (::map.isInitialized) {
            touristicPlaces.forEach { touristicPlace ->
                val marker = LatLng(touristicPlace.latitude, touristicPlace.longitude)
                map.addMarker(
                    MarkerOptions().position(marker).title(touristicPlace.name)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_place_img))
                )
            }
        }
    }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { _ ->
            lifecycleScope.launch {
                handleLocation()
            }
        }
}