package com.fernando.pokedex.infrastructure.managers

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.os.Looper
import com.fernando.pokedex.data.exceptions.PermissionException
import com.fernando.pokedex.data.mappers.toEntity
import com.fernando.pokedex.data.mappers.toLocationException
import com.fernando.pokedex.domain.entities.LocationEntity
import com.fernando.pokedex.domain.manager.LocationManager
import com.fernando.pokedex.ktx.hasPermission
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.Priority
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class LocationManagerImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val locationProvider: FusedLocationProviderClient
) : LocationManager {

    companion object {
        private const val TIMEOUT_MILLIS = 20 * 1000
    }

    private object Permission {
        const val LOCATION = Manifest.permission.ACCESS_FINE_LOCATION
    }

    private object Threshold {
        const val ANTIQUITY_MILLIS = 15 * 60 * 1000
        const val TIME_DIFFERENCE_MILLIS = 5 * 60 * 1000
    }

    private val locationRequest = LocationRequest.create().apply {
        interval = 0
        priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
    }

    private val locationRequestEvery = LocationRequest.Builder(5000L)
        .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
        .build()

    private lateinit var locationCallback: LocationCallback


    override suspend fun requestEnable() {
        val settingRequest = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)
            .build()

        try {
            LocationServices.getSettingsClient(context)
                .checkLocationSettings(settingRequest)
                .await()
        } catch (ex: ApiException) {
            throw ex.toLocationException()
        }
    }

    override fun onDistanceThresholdReached(): Flow<LocationEntity> = validate().run {
        return getLocationEverys()
    }

    @SuppressLint("MissingPermission")
    private fun getLocationEverys(): Flow<LocationEntity> = callbackFlow {
        var lastEmittedLocation: Location? = null
        var firstAccurateLocationReceived = false

        startLocationUpdate { locationResult ->
            val currentLocation = locationResult.getBetterLocationThan(lastEmittedLocation)

            if (!firstAccurateLocationReceived) {
                if (currentLocation != null && currentLocation.accuracy <= 20f) {
                    lastEmittedLocation = currentLocation
                    firstAccurateLocationReceived = true
                }
                return@startLocationUpdate
            }


            if (currentLocation == null || lastEmittedLocation == null) return@startLocationUpdate

            val distance = currentLocation.distanceTo(lastEmittedLocation!!)
            if (distance < 10f) return@startLocationUpdate


            lastEmittedLocation = currentLocation
            trySend(currentLocation.toEntity())
        }

        awaitClose { stopLocationUpdates() }
    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdate(callback: (LocationResult) -> Unit) {
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                locationResult
                callback(locationResult)
            }
        }
        locationProvider.requestLocationUpdates(
            locationRequestEvery,
            locationCallback,
            Looper.getMainLooper()
        )
    }


    private fun LocationResult.getBetterLocationThan(lastLocation: Location?): Location? {
        for (location in locations) {
            if (location.isBetterLocation(lastLocation)) {
                return location
            }
        }

        return null
    }

    private fun Location.isBetterLocation(oldLocationDto: Location?): Boolean {
        if (oldLocationDto == null) return true

        val timeDeltaMillis = time - oldLocationDto.time
        val isSignificantlyNewer = timeDeltaMillis > Threshold.TIME_DIFFERENCE_MILLIS
        val isSignificantlyOlder = timeDeltaMillis < -Threshold.TIME_DIFFERENCE_MILLIS
        val isNewer = timeDeltaMillis > 0

        if (isSignificantlyNewer)
            return true
        else if (isSignificantlyOlder)
            return false

        val accuracyDelta = (accuracy - oldLocationDto.accuracy).toInt()
        val isLessAccurate = accuracyDelta > 0
        val isMoreAccurate = accuracyDelta < 0
        val isSignificantlyLessAccurate = accuracyDelta > 200

        return if (isMoreAccurate) true
        else if (isNewer && !isLessAccurate) true
        else isNewer && !isSignificantlyLessAccurate
    }

    private fun hasTimeoutPassed(requestTimeMillis: Long): Boolean {
        val currentTimeMillis = System.currentTimeMillis()
        return requestTimeMillis - currentTimeMillis > TIMEOUT_MILLIS
    }

    private fun stopLocationUpdates() = locationProvider.removeLocationUpdates(locationCallback)

    private fun validate() {
        if (!context.hasPermission(Permission.LOCATION)) {
            throw PermissionException("Location permission required")
        }
    }
}