package com.jean.touraqp.touristicPlaces.presentation.map

import android.Manifest
import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.PopupMenu
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
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
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.Polyline
import com.google.android.gms.maps.model.PolylineOptions
import com.jean.touraqp.R
import com.jean.touraqp.touristicPlaces.presentation.model.TouristicPlaceWithReviewsUI
import com.jean.touraqp.touristicPlaces.presentation.shared.SharedViewModel
import com.jean.touraqp.touristicPlaces.presentation.shared.TouristicPlaceEffect
import com.jean.touraqp.touristicPlaces.presentation.shared.TouristicPlaceEvent
import com.jean.touraqp.touristicPlaces.utils.MapLocation
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import javax.inject.Inject

@AndroidEntryPoint
class MapScreenFragment : Fragment(R.layout.fragment_map_screen), OnMapReadyCallback {
    private val sharedViewModel: SharedViewModel by hiltNavGraphViewModels(R.id.core_graph)
    private val mapViewModel: MapViewModel by viewModels()
    private lateinit var map: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    var poly: Polyline? = null

    @Inject
    lateinit var mapLocation: MapLocation

    @Inject
    lateinit var apiService: ApiService

    companion object {
        const val TAG = "map_screen"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
    }

    private fun createRoute(start: LatLng, end: LatLng) {
        CoroutineScope(Dispatchers.IO).launch {
            val startCoordinates = "${start.longitude},${start.latitude}"
            val endCoordinates = "${end.longitude},${end.latitude}"
            val call = apiService
                .getRoute(getString(R.string.api_open_route_key), startCoordinates, endCoordinates)
            if (call.isSuccessful) {
                val routeResponse = call.body() as? RouteResponse
                withContext(Dispatchers.Main) {
                    drawRoute(routeResponse)
                }
            } else {
                Log.i("createRoute", "Route request failed")
            }
        }
    }

    private fun drawRoute(routeResponse: RouteResponse?) {
        poly?.remove()

        val polyLineOptions = PolylineOptions()
            .color(ContextCompat.getColor(requireContext(), R.color.blue))
            .width(10f)
        routeResponse?.features?.first()?.geometry?.coordinates?.forEach {
            polyLineOptions.add(LatLng(it[1], it[0]))
        }
        requireActivity().runOnUiThread {
            poly = map.addPolyline(polyLineOptions)
        }
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
        Log.i("mapa", "Mapa listo")
        onInfoWindowClickListener() // Registra el listener cuando el mapa esté listo
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
                                        Log.d("DEBUG STATE", "${mapViewModel.state.value}")
                                        Log.d("DEBUG STATE", "hasIdMarkerSelected : ${mapViewModel.state.value.hasIdMarkerSelected}")
                                    if (!mapViewModel.state.value.hasIdMarkerSelected) {
                                        moveCameraToLocation(location)
                                    }
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

                launch {
                    mapViewModel.state.collect() { state ->
                        if(state.hasIdMarkerSelected){
                            moveCameraToMarker(state.markers[state.idMarkerSelected])
                        }
                    }
                }
            }
        }
    }

    private fun moveCameraToMarker(marker: Marker?) {
        if(marker == null) return
        val location = LatLng(marker.position.latitude, marker.position.longitude)
        moveCameraToLocation(location)
        marker.showInfoWindow()
    }

    private fun moveCameraToLocation(location: LatLng) {
        map.moveCamera(
            CameraUpdateFactory.newLatLngZoom(
                location,
                14f
            )
        )
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
            val markersMap = mutableMapOf<String, Marker?>()

            touristicPlaces.forEach() { touristicPlace ->
                val markerLocation = LatLng(touristicPlace.latitude, touristicPlace.longitude)
                val markerAdded = map.addMarker(
                    MarkerOptions()
                        .position(markerLocation)
                        .title(touristicPlace.name)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_place_img))
                )
                markerAdded?.tag = touristicPlace.id
                //Add markers to state
                markersMap[touristicPlace.id] = markerAdded
            }

            mapViewModel.onEvent(MapEvent.OnAddMarkers(markersMap))
        }
    }

    private fun onInfoWindowClickListener() {
        map.setOnInfoWindowClickListener { marker ->
            val touristicPlaceId = marker.tag as? String ?: return@setOnInfoWindowClickListener

            // Proyección del mapa para convertir coordenadas geográficas a coordenadas de pantalla
            val projection = map.projection
            val screenLocation = projection.toScreenLocation(marker.position)

            // Encuentra el FrameLayout raíz para agregar una vista temporal
            val rootView = requireActivity().findViewById<ViewGroup>(android.R.id.content)

            // Crea una vista temporal como ancla del menú contextual
            val anchorView = View(requireContext())
            val layoutParams = FrameLayout.LayoutParams(1, 1)
            layoutParams.leftMargin = screenLocation.x
            layoutParams.topMargin = screenLocation.y
            anchorView.layoutParams = layoutParams
            rootView.addView(anchorView)

            // Crea y muestra el PopupMenu anclado a la vista temporal
            val popupMenu = PopupMenu(requireContext(), anchorView)
            popupMenu.menuInflater.inflate(R.menu.marker_menu, popupMenu.menu)

            popupMenu.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.action_show_details -> {
                        sharedViewModel.onEvent(
                            TouristicPlaceEvent.OnSelectTouristicPlace(
                                touristicPlaceId
                            )
                        )
                        true
                    }

                    R.id.action_show_route -> {
                        lifecycleScope.launch {
                            val startLocation = mapLocation.getCurrentUserLocation()
                            if (startLocation != null) {
                                val endLocation = marker.position
                                createRoute(startLocation, endLocation)
                            }
                        }
                        true
                    }

                    else -> false
                }
            }

            popupMenu.setOnDismissListener {
                // Elimina la vista temporal cuando el menú desaparezca
                rootView.removeView(anchorView)
            }

            popupMenu.show()
        }
    }

    private fun navigateToDetailScreen() {
        findNavController().navigate(
            MapScreenFragmentDirections.actionMapScreenFragmentToTouristicPlaceDetailScreenFragment()
        )
    }
}