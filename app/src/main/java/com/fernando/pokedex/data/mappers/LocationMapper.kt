package com.fernando.pokedex.data.mappers


import android.location.Location
import com.fernando.pokedex.data.exceptions.LocationException
import com.fernando.pokedex.domain.entities.LocationEntity
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationSettingsStatusCodes


fun Location.toEntity() = LocationEntity(
    latitude = this.latitude,
    longitude = this.longitude,
    accuracy = this.accuracy
)

internal fun ApiException.toLocationException() = when (statusCode) {
    LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> {
        val resolvable = this as ResolvableApiException
        LocationException(resolvable.resolution)
    }
    else -> LocationException()
}