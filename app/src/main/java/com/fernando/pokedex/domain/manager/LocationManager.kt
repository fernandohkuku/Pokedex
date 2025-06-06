package com.fernando.pokedex.domain.manager

import com.fernando.pokedex.domain.entities.LocationEntity
import kotlinx.coroutines.flow.Flow

interface LocationManager {
    suspend fun requestEnable()

    fun onDistanceThresholdReached(): Flow<LocationEntity>
}