package com.fernando.pokedex.domain.usecase.pokemon

import com.fernando.pokedex.domain.entities.PokemonEntity
import com.fernando.pokedex.domain.repositories.PokemonRepository
import com.fernando.pokedex.domain.usecase.base.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class DetectPokedexEntryUseCase @Inject constructor(
    private val repository: PokemonRepository,
    background: CoroutineDispatcher
) : UseCase<PokemonEntity, Unit>(background) {
    override suspend fun run(input: Unit?): PokemonEntity {
        return repository.detectPokedexEntry()
    }
}