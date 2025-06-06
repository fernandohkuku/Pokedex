package com.fernando.pokedex.domain.entities

import com.google.android.gms.maps.model.LatLng

data class LocationEntity(
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val accuracy: Float = 0f,
    val distance: Float? = 0f
) {
    val latLng: LatLng
        get() = LatLng(latitude, longitude)
}