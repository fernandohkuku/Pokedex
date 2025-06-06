package com.fernando.pokedex.di

import com.fernando.pokedex.domain.repositories.PokemonRepository
import com.fernando.pokedex.domain.usecase.pokemon.DetectPokedexEntryUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Provides
    @Singleton
    fun provideDetectPokedexEntryUseCase(
        repository: PokemonRepository,
        background: CoroutineDispatcher
    ): DetectPokedexEntryUseCase {
        return DetectPokedexEntryUseCase(repository, background)
    }
}