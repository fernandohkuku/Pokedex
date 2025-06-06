package com.fernando.pokedex.di

import com.fernando.pokedex.infrastructure.handlers.error.ApiErrorHandler
import com.fernando.pokedex.infrastructure.handlers.error.ErrorHandler
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface ErrorModule {

    @Binds
    fun bindErrorHandler(
        errorHandler: ApiErrorHandler
    ): ErrorHandler
}