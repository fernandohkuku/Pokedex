package com.fernando.pokedex.domain.usecase.location

import com.fernando.pokedex.domain.entities.LocationEntity
import com.fernando.pokedex.domain.manager.LocationManager
import com.fernando.pokedex.domain.usecase.base.UseCaseFlow
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLocationUseCase @Inject constructor(
    private val manager: LocationManager,
    background: CoroutineDispatcher
) : UseCaseFlow<LocationEntity, Unit>(background) {
    override fun run(input: Unit?): Flow<LocationEntity> {
        return manager.onDistanceThresholdReached()
    }
}