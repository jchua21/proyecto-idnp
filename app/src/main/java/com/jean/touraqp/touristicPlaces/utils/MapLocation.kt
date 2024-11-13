package com.jean.touraqp.touristicPlaces.utils

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import dagger.hilt.android.qualifiers.ActivityContext
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class MapLocation @Inject constructor(
    @ActivityContext private val context: Context,
) {

    private var fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    // Check if location permission is granted
    fun isLocationPermissionGranted(): Boolean =
        ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED


    @SuppressLint("MissingPermission")
    suspend fun getCurrentUserLocation(): LatLng? {
        return if (isLocationPermissionGranted()) {
            val location = fusedLocationClient.lastLocation.await()
            LatLng(location.latitude, location.longitude)
        } else {
            return null
        }
    }

    fun isInArequipa(userLocation: LatLng): Boolean {
        val arequipaBounds = LatLngBounds(
            LatLng(-16.4700, -71.5700),
            LatLng(-16.3000, -71.4500)
        )

        return arequipaBounds.contains(userLocation)
    }


    fun showShowRationale() = ActivityCompat.shouldShowRequestPermissionRationale(
        context as Activity, Manifest.permission.ACCESS_FINE_LOCATION
    )
}