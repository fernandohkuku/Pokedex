package com.fernando.pokedex.di

import android.content.Context
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class GoogleModule {
    @Singleton
    @Provides
    fun provideFusedLocationProviderClient(
        @ApplicationContext context: Context
    ) = LocationServices.getFusedLocationProviderClient(context)
}