package com.fernando.pokedex.data.exceptions

import android.app.PendingIntent

internal class LocationException(
    val resolution: PendingIntent? = null,
    override val message: String = "Location settings are not satisfied."
) : RuntimeException(message)