package com.fernando.pokedex.di

import com.fernando.pokedex.data.local.source.PokemonLocalDataSourceImpl
import com.fernando.pokedex.data.remote.source.PokemonRemoteDataSourceImpl
import com.fernando.pokedex.data.repositories.PokemonLocalDataSource
import com.fernando.pokedex.data.repositories.PokemonRemoteDataSource
import com.fernando.pokedex.data.repositories.PokemonRepositoryImpl
import com.fernando.pokedex.domain.repositories.PokemonRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
internal interface DataModule {

    @Binds
    fun bindPokemonRemoteDataSource(
        pokemonRemoteDataSourceImpl: PokemonRemoteDataSourceImpl
    ): PokemonRemoteDataSource


    @Binds
    fun bindPokemonLocalDataSource(
        pokemonLocalDataSourceImpl: PokemonLocalDataSourceImpl
    ): PokemonLocalDataSource

    @Binds
    fun bindPokemonRepository(
        pokemonRepositoryImpl: PokemonRepositoryImpl
    ): PokemonRepository
}