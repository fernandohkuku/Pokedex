package com.fernando.pokedex.di

import com.fernando.pokedex.domain.manager.LocationManager
import com.fernando.pokedex.infrastructure.managers.LocationManagerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface InfrastructureModule {

    @Binds
    fun bindLocationManager(impl: LocationManagerImpl): LocationManager
}