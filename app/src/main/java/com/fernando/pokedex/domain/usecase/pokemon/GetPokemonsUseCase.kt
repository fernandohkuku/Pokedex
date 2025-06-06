package com.fernando.pokedex.domain.usecase.pokemon

import com.fernando.pokedex.domain.entities.PokemonEntity
import com.fernando.pokedex.domain.repositories.PokemonRepository
import com.fernando.pokedex.domain.usecase.base.UseCaseFlow
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPokemonsUseCase @Inject constructor(
    private val repository: PokemonRepository,
    background: CoroutineDispatcher
) : UseCaseFlow<List<PokemonEntity>, Unit>(background) {
    override fun run(input: Unit?): Flow<List<PokemonEntity>> {
        return repository.getPokemons()
    }

}